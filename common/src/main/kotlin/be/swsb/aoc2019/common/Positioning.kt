package be.swsb.aoc2019.common

import be.swsb.aoc2019.common.Position.Companion.at
import java.io.Serializable

data class Quadrants<out T>(
        val topRight: T,
        val bottomRight: T,
        val topLeft: T,
        val bottomLeft: T
) : Serializable {

    /**
     * Returns string representation of the [Quadrants] including its [topRight], [bottomRight], [topLeft] and [bottomLeft] values.
     */
    override fun toString(): String = "\n|$topLeft|$topRight\n|$bottomLeft|$bottomRight|\n"

    companion object {}
}

//Thanks ICHBINI! I cleaned it up nicely ;)
/**
 * Partitions the given Positions into a Quadrants of Positions.
 * x = 0 is regarded as left, y = 0 is regarded as bottom,
 * So at 0,0 is in the bottom left quadrant
 */
fun Quadrants.Companion.quadrants(positions: Positions): Quadrants<Positions> {
    val (right, left) = positions.partition { it.x > 0 }
            .let { (right, left) ->
                right.partition { it.y > 0 } to left.partition { it.y > 0 }
            }
    return Quadrants(right.first, right.second, left.first, left.second)
}

/**
 * Converts this Quadrants into a list.
 */
fun <T> Quadrants<T>.toList(): List<T> = listOf(topRight, bottomRight, topLeft, bottomLeft)



typealias Positions = List<Position>

data class Position(val x: Int, val y: Int) {
    companion object {
        fun at(x: Int, y: Int): Position = Position(x, y)
    }
}

infix fun Position.until(other: Position): Positions {
    return when {
        this.x != other.x && this.y != other.y -> {
            throw java.lang.IllegalArgumentException("Cannot get a range for unrelated axis")
        }
        this == other -> {
            listOf(this)
        }
        this.x == other.x -> {
            (this.y range other.y).map { at(this.x, it) }
        }
        this.y == other.y -> {
            (this.x range other.x).map { at(it, this.y) }
        }
        else -> throw java.lang.IllegalArgumentException("I think we're not supposed to end up here")
    }
}

typealias ManhattanDistance = Int

infix fun Position.manhattanDistanceTo(other: Position): ManhattanDistance {
    return (this.x range other.x).toList().size + (this.y range other.y).toList().size
}

infix fun Int.range(other: Int): Iterable<Int> {
    return if (this < other) {
        (this + 1)..other
    } else {
        (this - 1).downTo(other)
    }
}
