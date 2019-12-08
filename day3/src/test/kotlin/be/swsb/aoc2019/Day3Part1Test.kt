package be.swsb.aoc2019

import be.swsb.aoc2019.common.Common
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3Part1Test {
    @Test
    fun `solve exercise 1`() {
        val wires = Common.readLinesAs("actualInput.txt", Common::asCsvLines)
        val result = solve(wires[0], wires[1])
        assertThat(result).isEqualTo(399)
    }
}
