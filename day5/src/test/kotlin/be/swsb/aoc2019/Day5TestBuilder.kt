package be.swsb.aoc2019

fun intCodes(vararg strings: String): IntCodes = IntCodes(strings.map { IntCode(it) })
