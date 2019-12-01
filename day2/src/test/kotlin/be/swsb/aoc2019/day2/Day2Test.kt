package be.swsb.aoc2019.day2

import be.swsb.aoc2019.common.Common.parseLinesAs
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day2Test {

    @Nested
    inner class Exercise1 {
        @Test
        fun `solve exercise 1`() {
            val modules = parseLinesAs("actualInput.txt", String::toInt)
        }
    }

    @Nested
    inner class Exercise2 {
        @Test
        fun `solve exercise 2`() {
            val modules = parseLinesAs("actualInput.txt", String::toInt)
        }
    }
}
