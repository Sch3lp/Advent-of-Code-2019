package be.swsb.aoc2019

data class IntCode(val value: Int) {

    val opCode: String
        get() = if (value < 10) "0$value" else value.toString().takeLast(2)

    override fun toString(): String {
        return value.toString()
    }
}

typealias IntCodes = List<IntCode>
