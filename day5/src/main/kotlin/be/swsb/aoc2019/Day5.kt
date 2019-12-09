package be.swsb.aoc2019

import be.swsb.aoc2019.Instruction.Companion.fromAListOfMax4Ints


fun solve(intCodes: List<Int>): Int {
    val opcodeStatements = parseInput(intCodes)

    var intCode = IntCodes(intCodes)
    opcodeStatements.forEach { opCodeStatement ->
        intCode = intCode.execute(opCodeStatement)
    }
    return intCode.single()
}


data class IntCodes(private val _intCodes: List<Int>) : List<Int> by _intCodes {

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
    }
}

typealias Address = Int

sealed class Instruction {
    data class Addition(val parameterAddress1: Address, val parameterAddress2: Address, val destinationAddress: Address) : Instruction()
    data class Multiplication(val parameterAddress1: Address, val parameterAddress2: Address, val destinationAddress: Address) : Instruction()
    object Halt : Instruction()
    object Noop : Instruction()

    companion object {
        fun fromAListOfMax4Ints(maxFourIntCodes: List<Int>): Instruction {
            if (maxFourIntCodes.size > 4) throw IllegalArgumentException("An Instruction cannot be made from more than 4 Intcodes")
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

fun parseInput(intCodes: List<Int>): List<Instruction> = intCodes.chunked(4) { fromAListOfMax4Ints(it) }

