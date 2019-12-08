package be.swsb.aoc2019

import be.swsb.aoc2019.Position.Companion.at
import be.swsb.aoc2019.WireDirection.*
import be.swsb.aoc2019.WireDirection.Companion.parseToWireDirection
import be.swsb.aoc2019.common.Common.asCsvLines
import be.swsb.aoc2019.common.Common.readLinesAs
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day3Test {

    @Nested
    inner class GeneralTests {
        @Test
        fun `Parsing into WireDirections`() {
            assertThat(parseToWireDirection("R1")).isEqualTo(Right(1))
            assertThat(parseToWireDirection("L20")).isEqualTo(Left(20))
            assertThat(parseToWireDirection("U999")).isEqualTo(Up(999))
            assertThat(parseToWireDirection("D42")).isEqualTo(Down(42))
            assertThatExceptionOfType(IllegalArgumentException::class.java)
                    .isThrownBy { parseToWireDirection("F00") }
        }

        @Test
        fun `look up crossed positions`() {
            val actual: List<Position> = lookUpCrossedPositions(listOf(at(1,1)),listOf(at(1,1)))

            assertThat(actual).containsExactly(at(1,1))
        }
    }

    @Nested
    inner class WireDirectionTests {
        @Test
        fun `pull in a WireDirection returns new Position`() {
            assertThat(at(1, -1).pull(Right(1))).containsExactly(at(2, -1))
            assertThat(at(1, -1).pull(Left(4))).containsExactly(
                    at(0, -1),
                    at(-1, -1),
                    at(-2, -1),
                    at(-3, -1)
            )
            assertThat(at(1, -1).pull(Up(1))).containsExactly(at(1, 0))
            assertThat(at(1, -1).pull(Down(3))).containsExactly(
                    at(1, -2),
                    at(1, -3),
                    at(1, -4)
            )
        }

        @Test
        fun `applying WireDirections returns a list of Positions`() {
            val positionsVisited = applyWireDirections(listOf(Right(2), Up(3)))

            assertThat(positionsVisited)
                    .containsExactly(
                            at(0,0), //initial position
                            at(1, 0), //go right
                            at(2, 0), //go right
                            at(2, 1), //go up
                            at(2, 2), //go up
                            at(2, 3) //go up
                    )
        }

        @Test
        fun `applying WireDirections resulting in a self-crossing wire`() {
            val positionsVisited = applyWireDirections(listOf(
                    Right(2),
                    Up(2),
                    Left(1),
                    Down(2),
                    Right(3)
            ))

            assertThat(positionsVisited)
                    .containsExactly(
                            at(0,0), //initial position
                            at(1, 0), //go right
                            at(2, 0), //go right
                            at(2, 1), //go up
                            at(2, 2), //go up
                            at(1, 2), //go left
                            at(1, 1), //go down
                            at(1, 0), //go down
                            at(2, 0), //go right
                            at(3, 0), //go right
                            at(4, 0) //go right
                    )
        }
    }

    @Nested
    inner class UntilTests {
        @Test
        fun `until with same Position returns a list of that one Position`() {
            assertThat(at(0, 0) until at(0, 0)).containsExactly(at(0, 0))
        }

        @Test
        fun `until returns list of all Positions between two Positions, excluding the start`() {
            assertThat(at(0, 0) until at(1, 0)).containsExactly(at(1, 0))
            assertThat(at(1, 0) until at(0, 0)).containsExactly(at(0, 0))
            assertThat(at(0, 0) until at(3, 0)).containsExactly(at(1, 0), at(2, 0), at(3, 0))
        }

        @Test
        fun `until should explode when positions are on different axis`() {
            assertThatExceptionOfType(java.lang.IllegalArgumentException::class.java)
                    .isThrownBy { at(0, 0) until at(1, 1) }
        }
    }

    @Nested
    inner class Exercise1 {

        @Test
        fun `AoC example 1`() {
            val result: Int = solve(
                    listOf("R75", "D30", "R83", "U83", "L12", "D49", "R71", "U7", "L72"),
                    listOf("U62", "R66", "U55", "R34", "D71", "R55", "D58", "R83")
            )

            assertThat(result).isEqualTo(159)
        }

        @Test
        fun `AoC example 2`() {
            val result: Int = solve(
                    listOf("R98", "U47", "R26", "D63", "R33", "U87", "L62", "D20", "R33", "U53", "R51"),
                    listOf("U98", "R91", "D20", "R16", "D67", "R40", "U7", "R15", "U6", "R7")
            )

            assertThat(result).isEqualTo(135)
        }

        @Test
        fun `solve exercise 1`() {
            val wires = readLinesAs("actualInput.txt", ::asCsvLines)
            val result = solve(wires[0], wires[1])
            assertThat(result).isEqualTo(0)
        }
    }


    @Nested
    inner class Exercise2 {
        @Test
        fun `solve exercise 2`() {

        }
    }
}
