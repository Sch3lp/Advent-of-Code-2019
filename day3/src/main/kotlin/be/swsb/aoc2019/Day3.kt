package be.swsb.aoc2019

// parse lines into wires
// parse U,D,L,R into wire direction commands
// keep positions for both wires
// keep positions where wires crossed (same positions in both lists)
// calculate manhattan distances from (0,0) to all crossed positions
// return closest distance

fun solve(wire1: List<String>, wire2: List<String>): Int = 0

sealed class WireDirection(val steps: Int) {
    data class Right(private val _steps:Int): WireDirection(_steps)
    data class Left(private val _steps:Int): WireDirection(_steps)
    data class Up(private val _steps:Int): WireDirection(_steps)
    data class Down(private val _steps:Int): WireDirection(_steps)

    companion object{
        fun parseToWireDirection(direction: String): WireDirection {
            val dir= direction[0]
            val steps = direction.substring(1)
            return when(dir) {
                'R' -> Right(steps.toInt())
                'L' -> Left(steps.toInt())
                'U' -> Up(steps.toInt())
                'D' -> Down(steps.toInt())
                else -> throw IllegalArgumentException("Could not parse $direction into a WireDirection.")
            }
        }
    }

}
