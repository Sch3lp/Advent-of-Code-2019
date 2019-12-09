package be.swsb.aoc2019

import be.swsb.aoc2019.Parameter.PositionMode
import be.swsb.aoc2019.ParameterValue.Address
import be.swsb.aoc2019.ParameterValue.Value


fun solve(intCodes: List<String>): Int {
    val memory: IntCodes = parseIntoIntCodes(intCodes)
    partiallyExecute(memory, null)
//    val instructions = parseInput(intCodes)

//    var intCode = IntCodes(intCodes)
//    instructions.forEach { instruction ->
//        intCode = intCode.execute(instruction)
//    }
//    return intCode.single()
    return 0
}

fun parseIntoIntCodes(intCodes: List<String>): IntCodes {
    return IntCodes(intCodes.map { IntCode(it) })
}

data class IntCode(private val _internalValue: String) {

}

data class IntCodes(private val _intCodes: List<IntCode>) : List<IntCode> by _intCodes {

    fun execute(instruction: Instruction): IntCodes {
        return IntCodes(emptyList())
    }
}

sealed class ParameterValue(val value: Int) {
    data class Address(private val _value: Int) : ParameterValue(_value)
    data class Value(private val _value: Int) : ParameterValue(_value)
}

sealed class Parameter(val value: ParameterValue) {
    data class PositionMode(private val _value: Address) : Parameter(_value) {
        constructor(_value: Int) : this(Address(_value))
    }

    data class ImmediateMode(private val _value: Value) : Parameter(_value) {
        constructor(_value: Int) : this(Value(_value))
    }
}

sealed class Instruction {
    data class Addition(val parameter1: Parameter? = null, val parameter2: Parameter? = null, val destinationAddress: PositionMode? = null) : Instruction()
    data class Multiplication(val parameter1: Parameter? = null, val parameter2: Parameter? = null, val destinationAddress: PositionMode? = null) : Instruction()
    object Halt : Instruction()
    object Execute : Instruction()

    companion object {

    }
}

fun partiallyExecute(memory: IntCodes, currentInstruction: Instruction?): Pair<IntCodes, Instruction?> {
    // memory -> state
    // gradually build up an instruction
    // when it's _complete_, execute it and apply it to memory
    return memory to currentInstruction
}


