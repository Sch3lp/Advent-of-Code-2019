package be.swsb.aoc2019.common

infix fun Int.range(other: Int): Iterable<Int> {
    return if (this < other) {
        (this + 1)..other
    } else {
        (this - 1).downTo(other)
    }
}
