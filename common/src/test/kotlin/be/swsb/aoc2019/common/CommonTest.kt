package be.swsb.aoc2019.common

import be.swsb.aoc2019.common.Common.parseLinesAs
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CommonTest {

    @Nested
    inner class FileReader {
        @Test
        internal fun `parseLines | can read lines as something`() {
            val actual = parseLinesAs("input.txt", String::toInt)

            assertThat(actual).containsExactly(12, 24)
        }
    }
}

