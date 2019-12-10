package be.swsb.aoc2019

import java.lang.IllegalArgumentException

enum class InputMode {
    PositionMode,
    Immediate;

    companion object {
        fun inputModeFrom(c: Char): InputMode = when (c) {
            '0' -> PositionMode
            '1' -> Immediate
            else -> throw IllegalArgumentException("Could not parse $c into an InputMode")
        }

    }
}
