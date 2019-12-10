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
    internal fun `execute Addition | adds param1 and param2 and stores it in destination`() {
        val memory = memory(101, 5, -1, 5, 1002, 2)
        val memoryAfterAddition = Addition(Immediate, PositionMode, -1, 2, Address(5))
                .execute(memory.pointerAt(4))
        assertThat(memoryAfterAddition).isEqualTo(memory(101, 5, -1, 5, 1002, 1).pointerAt(5))
    }

    @Test
    internal fun `loadParam1 | param1 is position mode and value of param1 is negative, throws exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy {
                    Addition(PositionMode, PositionMode)
                            .loadParam1(memory(1, -5, -1, 3, 1002, 2).pointerAt(1))
                }
    }

    @Test
    internal fun `loadParam1 | param1 is position mode, returns new Addition with param1 from memory position at pointer`() {
        val actual = Addition(PositionMode, PositionMode)
                .loadParam1(memory(1, 5, -1, 3, 1002, 2).pointerAt(1))

        assertThat(actual).isEqualTo(Addition(PositionMode, PositionMode, 2))
    }

    @Test
    internal fun `loadParam1 | param1 is immediate mode, returns new Addition with param1 from memory at pointer`() {
        val actual = Addition(Immediate, Immediate)
                .loadParam1(memory(1101, 5, -1, 3, 1002, 2).pointerAt(1))

        assertThat(actual).isEqualTo(Addition(Immediate, Immediate, 5))
    }

    @Test
    internal fun `loadParam2 | param2 is position mode and value of param2 is negative, throws exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy {
                    Addition(PositionMode, PositionMode)
                            .loadParam2(memory(1, -5, -1, 3, 1002, 2).pointerAt(2))
                }
    }

    @Test
    internal fun `loadParam2 | param2 is position mode, returns new Addition with param2 from memory position at pointer`() {
        val actual = Addition(PositionMode, PositionMode)
                .loadParam2(memory(1, 5, 1, 3, 1002, 2).pointerAt(2))

        assertThat(actual).isEqualTo(Addition(PositionMode, PositionMode, parameter2 = 5))
    }

    @Test
    internal fun `loadParam2 | param2 is immediate mode, returns new Addition with param2 from memory at pointer`() {
        val actual = Addition(Immediate, Immediate)
                .loadParam2(memory(1101, 5, -1, 3, 1002, 2).pointerAt(2))

        assertThat(actual).isEqualTo(Addition(Immediate, Immediate, parameter2 = -1))
    }


    @Test
    internal fun `loadDestination | destination's value is negative, throws exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy {
                    Addition(PositionMode, PositionMode)
                            .loadDestination(memory(1, 5, 1, -3, 1002, 2).pointerAt(3))
                }
    }

    @Test
    internal fun `loadDestination | destination's value is positive, returns new Addition with destination from memory position at pointer`() {
        val actual = Addition(PositionMode, PositionMode)
                .loadDestination(memory(1, 5, 1, 3, 1002, 2).pointerAt(3))

        assertThat(actual).isEqualTo(Addition(PositionMode, PositionMode, destination = Address(3)))
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
