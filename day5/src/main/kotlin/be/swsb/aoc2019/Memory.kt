package be.swsb.aoc2019

import java.lang.IllegalArgumentException

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

fun parseIntoMemory(intCodes: List<Int>): Memory {
    return Memory(intCodes)
}
