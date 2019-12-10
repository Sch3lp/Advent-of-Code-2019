package be.swsb.aoc2019

import be.swsb.aoc2019.Parameter.PositionMode
import be.swsb.aoc2019.ParameterValue.Address
import be.swsb.aoc2019.ParameterValue.Value
import java.lang.IllegalArgumentException


fun solve(intCodes: List<String>): Int {
    val memory: Memory = parseIntoIntCodes(intCodes.map { it.toInt() })
    partiallyExecute(memory, null)
//    val instructions = parseInput(intCodes)

//    var intCode = IntCodes(intCodes)
//    instructions.forEach { instruction ->
//        intCode = intCode.execute(instruction)
//    }
//    return intCode.single()
    return 0
}

fun parseIntoIntCodes(intCodes: List<Int>): Memory {
    return Memory(intCodes)
}

data class IntCode(val value: Int) {

    val opCode: String
        get() = if (value < 10) "0$value" else value.toString().takeLast(2)

    override fun toString(): String {
        return value.toString()
    }
}

typealias IntCodes = List<IntCode>

data class Memory(private val _intCodes: IntCodes, private val _instructionPointer: Int = 0) : IntCodes by _intCodes {

    val pointer: Int
        get() = _instructionPointer

    constructor(_intCodes: List<Int>) : this(_intCodes.map { IntCode(it) })

    fun increasePointer(): Memory {
        return this.copy(_instructionPointer = this._instructionPointer + 1)
    }

    override fun get(index: Int): IntCode = get(index, InputMode.Immediate)

    operator fun get(index: Int, mode: InputMode): IntCode = when (mode) {
        InputMode.Immediate -> {
            _intCodes[index]
        }
        InputMode.PositionMode -> {
            if (index < 0) throw IllegalArgumentException("Tried fetching memory at a negative position: $index")
            val newIndex = _intCodes[index].value
            if (newIndex < 0) throw IllegalArgumentException("Tried fetching memory at a negative position: $newIndex")
            _intCodes[newIndex]
        }
    }

    fun execute(instruction: Instruction): Memory {
        return Memory(emptyList<IntCode>())
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

enum class InputMode {
    PositionMode,
    Immediate
}

sealed class Instruction {
    data class Addition(val parameter1Mode: InputMode, val parameter2Mode: InputMode, val parameter1: Int? = null, val parameter2: Int? = null) : Instruction() {
        fun loadParam1(memory: Memory): Instruction {
            val valueAtMemory = memory[memory.pointer, parameter1Mode].value
            return this.copy(parameter1 = valueAtMemory)
        }

        fun loadParam2(memory: Memory): Instruction {
            val valueAtMemory = memory[memory.pointer, parameter2Mode].value
            return this.copy(parameter2 = valueAtMemory)
        }
    }

    data class Multiplication(val parameter1: Parameter? = null, val parameter2: Parameter? = null, val destinationAddress: PositionMode? = null) : Instruction()
    object Halt : Instruction()
    object Execute : Instruction()

    companion object {
        fun instructionFromIntCode(input: IntCode): Instruction {
            val intCode = input.toString()
            val opCode = input.opCode
            return when (opCode) {
                "01" -> additionFromString(intCode)
                else -> throw IllegalArgumentException("Could not parse $intCode into an Instruction")
            }
        }

        private fun additionFromString(inputString: String): Addition {
            if (inputString.length > 5) throw IllegalArgumentException("Could not parse $inputString into an Addition")
            val inputMode1 = inputModeFrom(inputString.getOrNull(inputString.length - 3) ?: '0')
            val inputMode2 = inputModeFrom(inputString.getOrNull(inputString.length - 4) ?: '0')
            requirePositionMode("Destination should always be in PositionMode") {
                inputModeFrom(inputString.getOrNull(inputString.length - 5) ?: '0')
            }
            return Addition(inputMode1, inputMode2)
        }

        private fun inputModeFrom(c: Char): InputMode = when (c) {
            '0' -> InputMode.PositionMode
            '1' -> InputMode.Immediate
            else -> throw IllegalArgumentException("Could not parse $c into an InputMode")
        }

        private fun requirePositionMode(errorMessage: String, block: () -> InputMode) {
            if (block() != InputMode.PositionMode) throw IllegalArgumentException(errorMessage)
        }
    }
}

fun partiallyExecute(memory: Memory, currentInstruction: Instruction?): Pair<Memory, Instruction?> {
    // memory -> state
    // gradually build up an instruction
    // when it's _complete_, execute it and apply it to memory

    val updatedInstruction = if (currentInstruction != null) {
        when (currentInstruction) {
            is Instruction.Addition -> {
                currentInstruction.loadParam1(memory)
            }
            else -> throw IllegalStateException("TODO")
        }
    } else {
        Instruction.instructionFromIntCode(memory[memory.pointer])
    }
    return memory.increasePointer() to updatedInstruction
}


