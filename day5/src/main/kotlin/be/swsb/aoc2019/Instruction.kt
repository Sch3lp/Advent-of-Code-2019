package be.swsb.aoc2019

import be.swsb.aoc2019.InputMode.Companion.inputModeFrom
import java.lang.IllegalArgumentException

interface Instruction {
    fun execute(memory: Memory): Memory
}

data class Operation(val parameter1Mode: InputMode,
                     val parameter2Mode: InputMode,
                     val parameter1: Int? = null,
                     val parameter2: Int? = null,
                     val destination: Address? = null,
                     val executeOperation: (p1: Int, p2: Int) -> Int = { _, _ -> 0 }) : Instruction {
    fun loadParam1(memory: Memory): Operation {
        val valueAtMemory = memory[memory.pointer, parameter1Mode].value
        return this.copy(parameter1 = valueAtMemory)
    }

    fun loadParam2(memory: Memory): Operation {
        val valueAtMemory = memory[memory.pointer, parameter2Mode].value
        return this.copy(parameter2 = valueAtMemory)
    }

    fun loadDestination(memory: Memory): Operation {
        val valueAtMemory = memory[memory.pointer].value
        return this.copy(destination = Address(valueAtMemory))
    }

    override fun execute(memory: Memory): Memory {
        return memory.set(this.destination!!, IntCode(executeOperation(this.parameter1!!, this.parameter2!!))).increasePointer()
    }

    companion object {
        fun addition(parameter1Mode: InputMode,
                     parameter2Mode: InputMode,
                     parameter1: Int? = null,
                     parameter2: Int? = null,
                     destination: Address? = null) : Operation {
            return Operation(
                    parameter1Mode = parameter1Mode,
                    parameter2Mode = parameter2Mode,
                    parameter1 = parameter1,
                    parameter2 = parameter2,
                    destination = destination,
                    executeOperation = { p1: Int, p2: Int -> p1 + p2 })
        }

        fun multiplication(parameter1Mode: InputMode,
                           parameter2Mode: InputMode,
                           parameter1: Int? = null,
                           parameter2: Int? = null,
                           destination: Address? = null) : Operation {
            return Operation(
                    parameter1Mode = parameter1Mode,
                    parameter2Mode = parameter2Mode,
                    parameter1 = parameter1,
                    parameter2 = parameter2,
                    destination = destination,
                    executeOperation = { p1: Int, p2: Int -> p1 * p2 })
        }
    }
}

fun instructionFromIntCode(input: IntCode): Instruction {
    val intCode = input.toString()
    return when (input.opCode) {
        "01" -> additionFromString(intCode)
        "02" -> multiplicationFromString(intCode)
        else -> throw IllegalArgumentException("Could not parse $intCode into an Instruction")
    }
}

private fun additionFromString(inputString: String): Operation {
    val (inputMode1, inputMode2) = operationFromString(inputString)
    return Operation.addition(inputMode1, inputMode2)
}

private fun multiplicationFromString(inputString: String): Operation {
    val (inputMode1, inputMode2) = operationFromString(inputString)
    return Operation.multiplication(inputMode1, inputMode2)
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

