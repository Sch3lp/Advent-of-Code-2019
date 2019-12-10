package be.swsb.aoc2019

fun memory(vararg ints: Int): Memory = Memory(ints.toList())

fun Memory.pointerAt(newPointer: Int): Memory = this.copy(_instructionPointer = newPointer)
