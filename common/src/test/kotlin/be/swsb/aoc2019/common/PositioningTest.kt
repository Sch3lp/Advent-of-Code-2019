package be.swsb.aoc2019.common

import be.swsb.aoc2019.common.Position.Companion.at
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PositioningTest {
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
            assertThatExceptionOfType(IllegalArgumentException::class.java)
                    .isThrownBy { at(0, 0) until at(1, 1) }
        }
    }


    @Nested
    inner class ManhattanDistanceTests {

        @Test
        fun `manhattanDistance to itself returns 0`() {
            assertThat(at(1, 0) manhattanDistanceTo at(1, 0)).isEqualTo(0)
            assertThat(at(-1, -5) manhattanDistanceTo at(-1, -5)).isEqualTo(0)
        }

        @Test
        fun `manhattanDistance crossing 0,0`() {
            assertThat(at(1, 1) manhattanDistanceTo at(-1, -1)).isEqualTo(4)
        }

        @Test
        fun `manhattanDistance x and y`() {
            assertThat(at(1, 0) manhattanDistanceTo at(-1, 0)).isEqualTo(2)
            assertThat(at(1, 0) manhattanDistanceTo at(1, -2)).isEqualTo(2)
            assertThat(at(1, 0) manhattanDistanceTo at(2, 2)).isEqualTo(3)
        }
    }

    @Nested
    inner class QuadrantsTests {
        @Test
        fun `can convert positions into quadrants`() {
            val quadrants = Quadrants.quadrants(listOf(
                    at(-1, 1),
                    at(1, 1),
                    at(1, -1),
                    at(-1, -1)
            ))

            assertThat(quadrants.topLeft).containsExactly(at(-1,1))
            assertThat(quadrants.topRight).containsExactly(at(1,1))
            assertThat(quadrants.bottomRight).containsExactly(at(1,-1))
            assertThat(quadrants.bottomLeft).containsExactly(at(-1,-1))
        }

        @Test
        fun `x = 0 is regarded as left, y = 0 is regarded as bottom`() {
            val quadrants = Quadrants.quadrants(listOf(
                    at(0, 1),
                    at(1, 0),
                    at(0, 0)
            ))

            assertSoftly {
                it.assertThat(quadrants.topLeft).containsExactly(at(0, 1))
                it.assertThat(quadrants.bottomRight).containsExactly(at(1, 0))
                it.assertThat(quadrants.bottomLeft).containsExactly(at(0, 0))
                it.assertThat(quadrants.topRight).isEmpty()
            }
        }


    }
}
