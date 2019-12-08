package be.swsb.aoc2019

import be.swsb.aoc2019.Position.Companion.at
import be.swsb.aoc2019.WireDirection.*

// parse lines into wires ✅
// parse U,D,L,R into wire direction commands ✅
// keep positions for both wires ✅
// keep positions where wires crossed (same positions in both lists) ✅
// calculate manhattan distances from (0,0) to all crossed positions
// return closest distance

fun solve(wire1: List<String>, wire2: List<String>): Int = 0

fun lookUpCrossedPositions(wire1: List<Position>, wire2: List<Position>): List<Position> =
     wire1.mapNotNull {
         wire2.find { wire2Pos -> wire2Pos == it }
     }

sealed class WireDirection(val steps: Int) {
    data class Right(private val _steps: Int) : WireDirection(_steps)
    data class Left(private val _steps: Int) : WireDirection(_steps)
    data class Up(private val _steps: Int) : WireDirection(_steps)
    data class Down(private val _steps: Int) : WireDirection(_steps)

    companion object {
        fun parseToWireDirection(direction: String): WireDirection {
            val dir = direction[0]
            val steps = direction.substring(1)
            return when (dir) {
                'R' -> Right(steps.toInt())
                'L' -> Left(steps.toInt())
                'U' -> Up(steps.toInt())
                'D' -> Down(steps.toInt())
                else -> throw IllegalArgumentException("Could not parse $direction into a WireDirection.")
            }
        }
    }
}

fun applyWireDirections(directions: List<WireDirection>): List<Position> {
    val visitedPositions = mutableListOf(at(0, 0))
    return directions.flatMapTo(visitedPositions) { visitedPositions.last().pull(it) }
}


data class Position(val x: Int, val y: Int) {
    fun pull(direction: WireDirection): List<Position> {
        return when (direction) {
            is Right -> this until at(this.x + direction.steps, this.y)
            is Left -> this until at(this.x - direction.steps, this.y)
            is Up -> this until at(this.x, this.y + direction.steps)
            is Down -> this until at(this.x, this.y - direction.steps)
        }
    }

    companion object {
        fun at(x: Int, y: Int): Position = Position(x, y)
    }
}

infix fun Position.until(position: Position): List<Position> {
    return when {
        this.x != position.x && this.y != position.y -> {
            throw java.lang.IllegalArgumentException("Cannot get a range for unrelated axis")
        }
        this == position -> {
            listOf(this)
        }
        this.x == position.x -> {
            (this.y range position.y).map { at(this.x, it) }
        }
        this.y == position.y -> {
            (this.x range position.x).map { at(it, this.y) }
        }
        else -> throw java.lang.IllegalArgumentException("I think we're not supposed to end up here")
    }
}

infix fun Int.range(other: Int): Iterable<Int> {
    return if (this < other) {
        (this + 1)..other
    } else {
        (this - 1).downTo(other)
    }
}
