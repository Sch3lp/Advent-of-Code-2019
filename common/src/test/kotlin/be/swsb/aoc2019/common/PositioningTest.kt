package be.swsb.aoc2019.common

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class PositioningTest {
    @Nested
    inner class UntilTests {
        @Test
        fun `until with same Position returns a list of that one Position`() {
            assertThat(Position.at(0, 0) until Position.at(0, 0)).containsExactly(Position.at(0, 0))
        }

        @Test
        fun `until returns list of all Positions between two Positions, excluding the start`() {
            assertThat(Position.at(0, 0) until Position.at(1, 0)).containsExactly(Position.at(1, 0))
            assertThat(Position.at(1, 0) until Position.at(0, 0)).containsExactly(Position.at(0, 0))
            assertThat(Position.at(0, 0) until Position.at(3, 0)).containsExactly(Position.at(1, 0), Position.at(2, 0), Position.at(3, 0))
        }

        @Test
        fun `until should explode when positions are on different axis`() {
            Assertions.assertThatExceptionOfType(IllegalArgumentException::class.java)
                    .isThrownBy { Position.at(0, 0) until Position.at(1, 1) }
        }
    }


    @Nested
    inner class ManhattanDistanceTests {

        @Test
        fun `manhattanDistance to itself returns 0`() {
            Assertions.assertThat(Position.at(1, 0) manhattanDistanceTo Position.at(1, 0)).isEqualTo(0)
            Assertions.assertThat(Position.at(-1, -5) manhattanDistanceTo Position.at(-1, -5)).isEqualTo(0)
        }

        @Test
        fun `manhattanDistance crossing 0,0`() {
            Assertions.assertThat(Position.at(1, 1) manhattanDistanceTo Position.at(-1, -1)).isEqualTo(4)
        }

        @Test
        fun `manhattanDistance x and y`() {
            Assertions.assertThat(Position.at(1, 0) manhattanDistanceTo Position.at(-1, 0)).isEqualTo(2)
            Assertions.assertThat(Position.at(1, 0) manhattanDistanceTo Position.at(1, -2)).isEqualTo(2)
            Assertions.assertThat(Position.at(1, 0) manhattanDistanceTo Position.at(2, 2)).isEqualTo(3)
        }
    }
}
