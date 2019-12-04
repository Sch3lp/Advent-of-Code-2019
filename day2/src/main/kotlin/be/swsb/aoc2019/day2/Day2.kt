package be.swsb.aoc2019.day2

import be.swsb.aoc2019.day2.Instruction.Companion.fromAListOfMax4Ints
import java.lang.IllegalArgumentException


fun solve(intCodes: List<Int>): Int {
    //keep two lists: one which contains parsed Opcodes (with positions and not values)
    //                other one which contains mutable values at positions
    //parse one Opcode line: consisting out of 4 Ints: The Opcode, the first position, the second position, the destination position
    //navigate to next Opcode
    //  recognizing 1 as addition, 2 as multiplication, 99 as the end, anything else as an exception
    //  do the operation (add or multiply)
    //  fetch the value at the given positions
    //  put the result in the destination position

    val opcodeStatements = parseInput(intCodes)

    // Got stuck on this statement pretty hard, and it's just a simple for each
    var intCode = IntCodes(intCodes)
    opcodeStatements.forEach { opCodeStatement ->
        intCode = intCode.execute(opCodeStatement)
    }
    return intCode.single()
}


// Thanks again to ICHBINI for the `by` suggestion
// `by` is in fact inheritance here! Wot?!
data class IntCodes(private val _intCodes: List<Int>) : List<Int> by _intCodes {

    // Thanks userman2 for the `inline` explanation
    fun execute(instruction: Instruction): IntCodes {
        return this.let {
            when (instruction) {
                is Instruction.Addition -> add(instruction)
                is Instruction.Multiplication -> multiply(instruction)
                is Instruction.Halt -> IntCodes(mutableListOf(_intCodes[0]))
                is Instruction.Noop -> IntCodes(_intCodes)
            }
        }
    }

    private fun add(instruction: Instruction.Addition): IntCodes {
        return IntCodes(_intCodes.toMutableList()
                .apply {
                    this[instruction.destinationAddress] = _intCodes[instruction.parameterAddress1] + _intCodes[instruction.parameterAddress2]
                }.toList())
    }

    private fun multiply(instruction: Instruction.Multiplication): IntCodes {
        return IntCodes(_intCodes.toMutableList()
                .apply {
                    this[instruction.destinationAddress] = _intCodes[instruction.parameterAddress1] * _intCodes[instruction.parameterAddress2]
                }.toList())
//        here's code with _intCodes as a MutableList:
//        _intCodes[opcodeStatement.destinationPosition] = _intCodes[opcodeStatement.position1] * _intCodes[opcodeStatement.position2]
//        return IntCodes(_intCodes)
//        another option would be to create an extension function on List.replace(index, newValue) to wrap this .toMutableList().apply{}.toList() stuff
//        suggested by ICHBINI
    }
}

sealed class Instruction {
    data class Addition(val parameterAddress1: Int, val parameterAddress2: Int, val destinationAddress: Int) : Instruction()
    data class Multiplication(val parameterAddress1: Int, val parameterAddress2: Int, val destinationAddress: Int) : Instruction()
    object Halt : Instruction()
    object Noop : Instruction()

    companion object {
        fun fromAListOfMax4Ints(maxFourIntCodes: List<Int>): Instruction {
            if (maxFourIntCodes.size > 4) throw IllegalArgumentException("OpcodeStatements cannot be made from 4 Intcodes")
            return parse(maxFourIntCodes)
        }

        private fun parse(maxFourIntCodes: List<Int>): Instruction =
                when (maxFourIntCodes[0]) {
                    1 -> Addition(maxFourIntCodes[1], maxFourIntCodes[2], maxFourIntCodes[3])
                    2 -> Multiplication(maxFourIntCodes[1], maxFourIntCodes[2], maxFourIntCodes[3])
                    99 -> Halt
                    else -> Noop
                }
    }
}

// Thank you so much ICHBINI for the `.chunked` suggestion
fun parseInput(intCodes: List<Int>): List<Instruction> = intCodes.chunked(4) { fromAListOfMax4Ints(it) }

