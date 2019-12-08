package be.swsb.aoc2019

import be.swsb.aoc2019.Position.Companion.at
import be.swsb.aoc2019.WireDirection.*
import be.swsb.aoc2019.WireDirection.Companion.parseToWireDirection
import be.swsb.aoc2019.common.Common.csvLines
import be.swsb.aoc2019.common.Common.readLinesAs
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day3Test {

    @Test
    internal fun `Parsing into WireDirections`() {
        assertThat(parseToWireDirection("R1")).isEqualTo(Right(1))
        assertThat(parseToWireDirection("L20")).isEqualTo(Left(20))
        assertThat(parseToWireDirection("U999")).isEqualTo(Up(999))
        assertThat(parseToWireDirection("D42")).isEqualTo(Down(42))
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy { parseToWireDirection("F00") }
    }

    @Nested
    inner class Exercise1 {

        @Test
        internal fun `AoC example 1`() {
            val result: Int = solve(
                    listOf("R75", "D30", "R83", "U83", "L12", "D49", "R71", "U7", "L72"),
                    listOf("U62", "R66", "U55", "R34", "D71", "R55", "D58", "R83")
            )

            assertThat(result).isEqualTo(159)
        }

        @Test
        internal fun `AoC example 2`() {
            val result: Int = solve(
                    listOf("R98", "U47", "R26", "D63", "R33", "U87", "L62", "D20", "R33", "U53", "R51"),
                    listOf("U98", "R91", "D20", "R16", "D67", "R40", "U7", "R15", "U6", "R7")
            )

            assertThat(result).isEqualTo(135)
        }

        @Test
        fun `solve exercise 1`() {
            val wires = readLinesAs("actualInput.txt", ::csvLines)
            val result = solve(wires[0],wires[1])
            assertThat(result).isEqualTo(4090689)
        }
    }


    @Nested
    inner class Exercise2 {
        @Test
        fun `solve exercise 2`() {

        }
    }
}
