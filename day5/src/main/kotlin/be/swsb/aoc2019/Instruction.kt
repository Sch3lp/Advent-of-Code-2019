package be.swsb.aoc2019

import be.swsb.aoc2019.InputMode.Companion.inputModeFrom
import java.lang.IllegalArgumentException

sealed class Instruction {
    data class Addition(val parameter1Mode: InputMode,
                        val parameter2Mode: InputMode,
                        val parameter1: Int? = null,
                        val parameter2: Int? = null,
                        val destination: Address? = null) : Instruction() {
        fun loadParam1(memory: Memory): Addition {
            val valueAtMemory = memory[memory.pointer, parameter1Mode].value
            return this.copy(parameter1 = valueAtMemory)
        }

        fun loadParam2(memory: Memory): Addition {
            val valueAtMemory = memory[memory.pointer, parameter2Mode].value
            return this.copy(parameter2 = valueAtMemory)
        }

        fun loadDestination(memory: Memory): Addition {
            val valueAtMemory = memory[memory.pointer].value
            return this.copy(destination = Address(valueAtMemory))
        }

        fun execute(memory: Memory): Memory {
            return memory.set(this.destination!!, IntCode(executeOperation())).increasePointer()
        }

        private fun executeOperation(): Int = parameter1!! + parameter2!!
    }

    data class Multiplication(val parameter1Mode: InputMode,
                        val parameter2Mode: InputMode,
                        val parameter1: Int? = null,
                        val parameter2: Int? = null,
                        val destination: Address? = null) : Instruction() {
        fun loadParam1(memory: Memory): Multiplication {
            val valueAtMemory = memory[memory.pointer, parameter1Mode].value
            return this.copy(parameter1 = valueAtMemory)
        }

        fun loadParam2(memory: Memory): Multiplication {
            val valueAtMemory = memory[memory.pointer, parameter2Mode].value
            return this.copy(parameter2 = valueAtMemory)
        }

        fun loadDestination(memory: Memory): Multiplication {
            val valueAtMemory = memory[memory.pointer].value
            return this.copy(destination = Address(valueAtMemory))
        }

        fun execute(memory: Memory): Memory {
            return memory.set(this.destination!!, IntCode(executeOperation())).increasePointer()
        }

        private fun executeOperation(): Int = parameter1!! * parameter2!!
    }

    companion object {
        fun instructionFromIntCode(input: IntCode): Instruction {
            val intCode = input.toString()
            return when (input.opCode) {
                "01" -> additionFromString(intCode)
                "02" -> multiplicationFromString(intCode)
                else -> throw IllegalArgumentException("Could not parse $intCode into an Instruction")
            }
        }

        private fun additionFromString(inputString: String): Addition {
            val (inputMode1, inputMode2) = operationFromString(inputString)
            return Addition(inputMode1, inputMode2)
        }

        private fun multiplicationFromString(inputString: String): Multiplication {
            val (inputMode1, inputMode2) = operationFromString(inputString)
            return Multiplication(inputMode1, inputMode2)
        }

        private fun operationFromString(inputString: String): Pair<InputMode, InputMode> {
            if (inputString.length > 5) throw IllegalArgumentException("Could not parse $inputString into an Addition")
            val inputMode1 = inputModeFrom(inputString.getOrNull(inputString.length - 3) ?: '0')
            val inputMode2 = inputModeFrom(inputString.getOrNull(inputString.length - 4) ?: '0')
            requirePositionMode("Destination should always be in PositionMode") {
                inputModeFrom(inputString.getOrNull(inputString.length - 5) ?: '0')
            }
            return Pair(inputMode1, inputMode2)
        }

        private fun requirePositionMode(errorMessage: String, block: () -> InputMode) {
            if (block() != InputMode.PositionMode) throw IllegalArgumentException(errorMessage)
        }
    }
}
