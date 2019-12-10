package be.swsb.aoc2019

import be.swsb.aoc2019.InputMode.Immediate
import be.swsb.aoc2019.InputMode.PositionMode
import be.swsb.aoc2019.Instruction.Addition
import be.swsb.aoc2019.Instruction.Companion.instructionFromIntCode
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

@Nested
class InstructionTest {
    @Test
    internal fun `loadParam1 | param1 is position mode and value of param1 is negative, throws exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy {
                    Addition(PositionMode, PositionMode)
                            .loadParam1(memory(1, -5, -1, 3, 1002, 2).increasePointer())
                }
    }

    @Test
    internal fun `loadParam1 | param1 is position mode, returns new Addition with param1 from memory position at pointer`() {
        val actual = Addition(PositionMode, PositionMode)
                .loadParam1(memory(1, 5, -1, 3, 1002, 2).increasePointer())

        assertThat(actual).isEqualTo(Addition(PositionMode, PositionMode, 2))
    }

    @Test
    internal fun `loadParam1 | param1 is immediate mode, returns new Addition with param1 from memory at pointer`() {
        val actual = Addition(Immediate, Immediate)
                .loadParam1(memory(1101, 5, -1, 3, 1002, 2).increasePointer())

        assertThat(actual).isEqualTo(Addition(Immediate, Immediate, 5))
    }

    @Test
    internal fun `loadParam2 | param2 is position mode and value of param2 is negative, throws exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy {
                    Addition(PositionMode, PositionMode)
                            .loadParam2(memory(1, -5, -1, 3, 1002, 2).increasePointer())
                }
    }

    @Test
    internal fun `loadParam2 | param2 is position mode, returns new Addition with param2 from memory position at pointer`() {
        val actual = Addition(PositionMode, PositionMode)
                .loadParam2(memory(1, 5, 1, 3, 1002, 2).increasePointer().increasePointer())

        assertThat(actual).isEqualTo(Addition(PositionMode, PositionMode, parameter2 = 5))
    }

    @Test
    internal fun `loadParam2 | param2 is immediate mode, returns new Addition with param2 from memory at pointer`() {
        val actual = Addition(Immediate, Immediate)
                .loadParam2(memory(1101, 5, -1, 3, 1002, 2).increasePointer().increasePointer())

        assertThat(actual).isEqualTo(Addition(Immediate, Immediate, parameter2 = -1))
    }

    @Test
    internal fun `instructionFromString | Addition from an invalid string throws an exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy { instructionFromIntCode(IntCode(5501)) }
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy { instructionFromIntCode(IntCode(111101)) }
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy { instructionFromIntCode(IntCode(11101)) }
    }

    @Test
    internal fun `instructionFromString | Addition can be created from a valid string`() {
        assertThat(instructionFromIntCode(IntCode(1))).isEqualTo(Addition(PositionMode, PositionMode))
        assertThat(instructionFromIntCode(IntCode(1001))).isEqualTo(Addition(PositionMode, Immediate))
        assertThat(instructionFromIntCode(IntCode(1101))).isEqualTo(Addition(Immediate, Immediate))
        assertThat(instructionFromIntCode(IntCode(101))).isEqualTo(Addition(Immediate, PositionMode))
    }
}
