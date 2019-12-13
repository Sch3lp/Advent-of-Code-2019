package be.swsb.aoc2019

import be.swsb.aoc2019.InputMode.Immediate
import be.swsb.aoc2019.InputMode.PositionMode
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

@Nested
class InstructionTest {

    @Test
    fun `execute Addition | adds param1 and param2 and stores it in destination`() {
        val memory = memory(101, -1, 2, 5, 1002, 2)
        val memoryAfterAddition = Operation.addition(Immediate, PositionMode, -1, 2, Address(5))
                .execute(memory.pointerAt(4))
        assertThat(memoryAfterAddition).isEqualTo(memory(101, -1, 2, 5, 1002, 1).pointerAt(5))
    }

    @Test
    fun `execute Multiplication | multiplies param1 with param2 and stores it in destination`() {
        val memory = memory(102, -2, 6, 5, 1002, 2)
        val memoryAfterAddition = Operation.multiplication(Immediate, PositionMode, -2, 6, Address(5))
                .execute(memory.pointerAt(4))
        assertThat(memoryAfterAddition).isEqualTo(memory(102, -2, 6, 5, 1002, -12).pointerAt(5))
    }

    @Test
    fun `loadParam1 | param1 is position mode and value of param1 is negative, throws exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy {
                    Operation(PositionMode, PositionMode)
                            .loadParam1(memory(1, -5, -1, 3, 1002, 2).pointerAt(1))
                }
    }

    @Test
    fun `loadParam1 | param1 is position mode, returns new Operation with param1 from memory position at pointer`() {
        val actual = Operation(PositionMode, PositionMode)
                .loadParam1(memory(1, 5, -1, 3, 1002, 2).pointerAt(1))

        assertThat(actual).isEqualTo(Operation(PositionMode, PositionMode, 2))
    }

    @Test
    fun `loadParam1 | param1 is immediate mode, returns new Operation with param1 from memory at pointer`() {
        val actual = Operation(Immediate, Immediate)
                .loadParam1(memory(1101, 5, -1, 3, 1002, 2).pointerAt(1))

        assertThat(actual).isEqualTo(Operation(Immediate, Immediate, 5))
    }

    @Test
    fun `loadParam2 | param2 is position mode and value of param2 is negative, throws exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy {
                    Operation(PositionMode, PositionMode)
                            .loadParam2(memory(1, -5, -1, 3, 1002, 2).pointerAt(2))
                }
    }

    @Test
    fun `loadParam2 | param2 is position mode, returns new Operation with param2 from memory position at pointer`() {
        val actual = Operation(PositionMode, PositionMode)
                .loadParam2(memory(1, 5, 1, 3, 1002, 2).pointerAt(2))

        assertThat(actual).isEqualTo(Operation(PositionMode, PositionMode, parameter2 = 5))
    }

    @Test
    fun `loadParam2 | param2 is immediate mode, returns new Operation with param2 from memory at pointer`() {
        val actual = Operation(Immediate, Immediate)
                .loadParam2(memory(1101, 5, -1, 3, 1002, 2).pointerAt(2))

        assertThat(actual).isEqualTo(Operation(Immediate, Immediate, parameter2 = -1))
    }


    @Test
    fun `loadDestination | destination's value is negative, throws exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy {
                    Operation(PositionMode, PositionMode)
                            .loadDestination(memory(1, 5, 1, -3, 1002, 2).pointerAt(3))
                }
    }

    @Test
    fun `loadDestination | destination's value is positive, returns new Operation with destination from memory position at pointer`() {
        val actual = Operation(PositionMode, PositionMode)
                .loadDestination(memory(1, 5, 1, 3, 1002, 2).pointerAt(3))

        assertThat(actual).isEqualTo(Operation(PositionMode, PositionMode, destination = Address(3)))
    }

    @Test
    fun `instructionFromString | Operation from an invalid string throws an exception`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy { instructionFromIntCode(IntCode(5501)) }
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy { instructionFromIntCode(IntCode(111101)) }
        assertThatExceptionOfType(IllegalArgumentException::class.java)
                .isThrownBy { instructionFromIntCode(IntCode(11101)) }
    }

    @Test
    fun `instructionFromString | Addition can be created from a valid string`() {
        assertThat(instructionFromIntCode(IntCode(1))).isEqualTo(Operation.addition(PositionMode, PositionMode))
        assertThat(instructionFromIntCode(IntCode(1001))).isEqualTo(Operation.addition(PositionMode, Immediate))
        assertThat(instructionFromIntCode(IntCode(1101))).isEqualTo(Operation.addition(Immediate, Immediate))
        assertThat(instructionFromIntCode(IntCode(101))).isEqualTo(Operation.addition(Immediate, PositionMode))
    }

    @Test
    fun `instructionFromString | Multiplication can be created from a valid string`() {
        assertThat(instructionFromIntCode(IntCode(2))).isEqualTo(Operation.multiplication(PositionMode, PositionMode))
        assertThat(instructionFromIntCode(IntCode(1002))).isEqualTo(Operation.multiplication(PositionMode, Immediate))
        assertThat(instructionFromIntCode(IntCode(1102))).isEqualTo(Operation.multiplication(Immediate, Immediate))
        assertThat(instructionFromIntCode(IntCode(102))).isEqualTo(Operation.multiplication(Immediate, PositionMode))
    }
}
