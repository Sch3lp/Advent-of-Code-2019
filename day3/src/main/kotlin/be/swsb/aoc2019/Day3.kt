package be.swsb.aoc2019

import be.swsb.aoc2019.WireDirection.*
import be.swsb.aoc2019.common.*
import be.swsb.aoc2019.common.Position.Companion.at


// optimize find intersections to work with quadrants
// find intersections
// applyWireDirections until (and including) every intersection and keep the amount of steps
// do that for every wire
// then we have the amount of steps it takes to reach every intersection, per wire
// then make sums of all combinations (wire1[123,19,44] * wire2[1256,552,23])
// and find the sum with the least amount of steps

fun solve2(wire1: List<String>, wire2: List<String>): Int {
    return 0
}

// parse lines into wires ✅
// parse U,D,L,R into wire direction commands ✅
// keep positions for both wires ✅
// keep positions where wires crossed (same positions in both lists) ✅
// calculate manhattan distances from (0,0) to all crossed positions ✅
// return closest distance ✅

fun solve(wire1: List<String>, wire2: List<String>): Int {
    val mess1 = measure("mess1") {
        applyWireDirections(wire1.map { WireDirection.parseToWireDirection(it) })
    }
    val mess2 = measure("mess2") {
        applyWireDirections(wire2.map { WireDirection.parseToWireDirection(it) })
    }
    val intersections = measure("findIntersections") {
        findIntersections(mess1, mess2)
    }
    return intersections
            .filterNot { it == at(0, 0) }
            .map { it manhattanDistanceTo at(0, 0) }.min()!!
}

fun findIntersections(wire1: Positions, wire2: Positions): Positions {
    val wire1Quadrants = Quadrants.quadrants(wire1)
    val wire2Quadrants = Quadrants.quadrants(wire2)

    val topRightIntersections = wire1Quadrants.topRight.mapNotNull {
        wire2Quadrants.topRight.find { wire2Pos -> wire2Pos == it }
    }
    val bottomRightIntersections = wire1Quadrants.bottomRight.mapNotNull {
        wire2Quadrants.bottomRight.find { wire2Pos -> wire2Pos == it }
    }
    val topLeftIntersections = wire1Quadrants.topLeft.mapNotNull {
        wire2Quadrants.topLeft.find { wire2Pos -> wire2Pos == it }
    }
    val bottomLeftIntersections = wire1Quadrants.bottomLeft.mapNotNull {
        wire2Quadrants.bottomLeft.find { wire2Pos -> wire2Pos == it }
    }

    return topRightIntersections + bottomRightIntersections + topLeftIntersections + bottomLeftIntersections
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

fun applyWireDirections(directions: List<WireDirection>): Positions {
    val visitedPositions = mutableListOf(at(0, 0))
    return directions.flatMapTo(visitedPositions) { visitedPositions.last().pull(it) }
}

fun Position.pull(direction: WireDirection): Positions {
    return when (direction) {
        is Right -> this until at(this.x + direction.steps, this.y)
        is Left -> this until at(this.x - direction.steps, this.y)
        is Up -> this until at(this.x, this.y + direction.steps)
        is Down -> this until at(this.x, this.y - direction.steps)
    }
}
