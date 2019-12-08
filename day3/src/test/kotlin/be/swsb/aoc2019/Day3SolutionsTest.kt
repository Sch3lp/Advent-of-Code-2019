package be.swsb.aoc2019

import be.swsb.aoc2019.common.Files
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3SolutionsTest {
    @Test
    fun `solve exercise 1`() {
        val wires = Files.readLinesAs("actualInput.txt", Files::asCsvLines)
        val result = solve(wires[0], wires[1])
        assertThat(result).isEqualTo(399)
    }

    @Test
    fun `solve exercise 2`() {
        val wires = Files.readLinesAs("actualInput.txt", Files::asCsvLines)
        val result = solve2(wires[0], wires[1])
        assertThat(result).isEqualTo(15678)
    }

}
