package be.swsb.aoc2019.common

import be.swsb.aoc2019.common.Common.csvLinesAs
import be.swsb.aoc2019.common.Common.readLinesAs
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CommonTest {

    @Nested
    inner class FileReader {
        @Test
        internal fun `readLines | can read lines as something`() {
            val actual = readLinesAs("input.txt", String::toInt)

            assertThat(actual).containsExactly(12, 24)
        }

        @Test
        internal fun `csvLines | can read lines as something after splitting on ','`() {
            val actual = csvLinesAs("csvinput.txt", String::toInt)

            assertThat(actual).containsExactly(12, 24, 32, 48)
        }
    }
}

