package be.swsb.aoc2019

import be.swsb.aoc2019.InputMode.PositionMode
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

@Nested
class MemoryTest {

    @Test
    internal fun `parseIntoIntCodes | Strings get turned into IntCodes`() {
        val intCodes = parseIntoMemory(listOf(1001, 1, -1, 4, 0))

        assertThat(intCodes).containsExactly(
                IntCode(1001),
                IntCode(1),
                IntCode(-1),
                IntCode(4),
                IntCode(0))
    }

    @Test
    internal fun `get | returns value at memory index`() {
        val memory = memory(1, 2, 3, 4, 5)
        assertThat(memory[0].value).isEqualTo(1)
        assertThat(memory[1].value).isEqualTo(2)
        assertThat(memory[2].value).isEqualTo(3)
        assertThat(memory[3].value).isEqualTo(4)
        assertThat(memory[4].value).isEqualTo(5)
    }

    @Test
    internal fun `get | with Mode returns value from memory depending on mode`() {
        val memory = memory(1, 20, 30, 40, 50)
        assertThat(memory[0, PositionMode].value).isEqualTo(20)
    }

    @Test
    internal fun `get | with Position Mode and negative value, throws exception`() {
        val memory = memory(1, 20, 30, 40, 50)
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy { memory[-1, PositionMode] }
    }
}
