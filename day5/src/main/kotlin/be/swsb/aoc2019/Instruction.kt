package be.swsb.aoc2019

import java.lang.IllegalArgumentException

sealed class Instruction {
    data class Addition(val parameter1Mode: InputMode, val parameter2Mode: InputMode, val parameter1: Int? = null, val parameter2: Int? = null) : Instruction() {
        fun loadParam1(memory: Memory): Addition {
            val valueAtMemory = memory[memory.pointer, parameter1Mode].value
            return this.copy(parameter1 = valueAtMemory)
        }

        fun loadParam2(memory: Memory): Addition {
            val valueAtMemory = memory[memory.pointer, parameter2Mode].value
            return this.copy(parameter2 = valueAtMemory)
        }
    }

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
