package be.swsb.aoc2019

import java.lang.IllegalArgumentException

data class Address(val value: Int) {
    init {
        if (value < 0) throw IllegalArgumentException("An Address can't have a negative value")
    }

    operator fun plus(value: Int): Address {
        return this.copy(value = this.value + value)
    }
}

data class Memory(private val _intCodes: IntCodes, private val _instructionPointer: Address = Address(0)) : IntCodes by _intCodes {

    val pointer: Address
        get() = _instructionPointer

    constructor(_intCodes: List<Int>) : this(_intCodes.map { IntCode(it) })

    fun increasePointer(): Memory {
        return this.copy(_instructionPointer = this._instructionPointer + 1)
    }

    fun set(index: Address, value: IntCode): Memory {
        val newIntCodes = _intCodes.toMutableList()
        newIntCodes[index.value] = value
        return this.copy(_intCodes = newIntCodes)
    }

    operator fun get(index: Address): IntCode = get(index, InputMode.Immediate)

    operator fun get(index: Address, mode: InputMode): IntCode = when (mode) {
        InputMode.Immediate -> {
            _intCodes[index.value]
        }
        InputMode.PositionMode -> {
            val newIndex = _intCodes[index.value].value
            if (newIndex < 0) throw IllegalArgumentException("Tried fetching memory at a negative position: $newIndex")
            _intCodes[newIndex]
        }
    }
}

fun parseIntoMemory(intCodes: List<Int>): Memory {
    return Memory(intCodes)
}
