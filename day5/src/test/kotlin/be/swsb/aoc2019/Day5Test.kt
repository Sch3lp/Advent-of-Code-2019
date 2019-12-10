package be.swsb.aoc2019

import be.swsb.aoc2019.InputMode.Immediate
import be.swsb.aoc2019.InputMode.PositionMode
import be.swsb.aoc2019.Instruction.*
import be.swsb.aoc2019.Instruction.Companion.instructionFromIntCode
import be.swsb.aoc2019.common.Files.csvLines
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.lang.IllegalArgumentException


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day5Test {

    //Questions:
    // it says "and so on", so are there an infinite amount of parameters possible?
    // or am I just silly and it depends merely on the sort of instruction in the 2 right most digits?

    @Nested
    inner class ParsingIntoMemory {
        @Test
        internal fun `parseIntoIntCodes | Strings get turned into IntCodes`() {
            val intCodes = parseIntoIntCodes(listOf(1001, 1, -1, 4, 0))

            assertThat(intCodes).containsExactly(
                    IntCode(1001),
                    IntCode(1),
                    IntCode(-1),
                    IntCode(4),
                    IntCode(0))
        }
    }

    @Nested
    inner class ParsingIntoInstructions {

        /*
        given : "1001", "100", "-1", "3", "1002", ..., 3

        parse "1001"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Addition(PositionMode, ImmediateMode)

        parse "100"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Addition(Address(100))

        parse "-1"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Addition(Address(100),Value(-1))

        parse "3"
        memory = "1001", "100", "-1", "2", "1002", ..., 3
        currentInstruction = Addition(Address(100),Value(-1),DestinationAt(3))

        parse "1002"
        memory = "1001", "100", "-1", "2", "1002", ..., 3
        currentInstruction = Multiplication
         */

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

        @Test
        internal fun `parseInput | gradually parses into an Instruction and executes it`() {
            val memory = memory(1001, 5, -1, 3, 1002, 2)
            val memoryAfterAddition = memory(1001, 5, -1, 1, 1002, 2)

            assertThat(partiallyExecute(memory, null))
                    .isEqualTo(memory.increasePointer() to Addition(PositionMode, Immediate))

            assertThat(partiallyExecute(memory.increasePointer(), Addition(PositionMode, Immediate)))
                    .isEqualTo(memory.increasePointer().increasePointer() to Addition(PositionMode, Immediate, 2))
//
//            assertThat(partiallyExecute(memory, Addition(PositionMode(5), ImmediateMode(-1))))
//                    .isEqualTo(memory to Addition(PositionMode(5), ImmediateMode(-1), PositionMode(3)))
//
//            assertThat(partiallyExecute(memory, Addition(PositionMode(5), ImmediateMode(-1), PositionMode(3))))
//                    .isEqualTo(memoryAfterAddition to Multiplication())
        }
    }

    @Nested
    inner class AdditionTests {
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
    }

    @Nested
    inner class MemoryTests {
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

    @Nested
    inner class ExecutingInstructions {
        @Test
        internal fun `execute | applying Addition, applies addition on the given positions and returns an updated Intcodes`() {
//            val intCodes = IntCodes(listOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50))
        }

        @Test
        internal fun `execute | applying Multiplication, applies multiplication on the given positions and returns an updated Intcodes`() {
//            val intCodes = IntCodes(listOf(1, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50))
        }

        @Test
        internal fun `execute | applying Halt, returns Intcodes with just position 0`() {
//            val intCodes = IntCodes(listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50))
        }
    }

    @Nested
    inner class Exercise1 {

        @Test
        internal fun `example where applying an instruction to an immediate parameter that was changed by a previous instruction`() {
//            execute(intCodes("1101","2","3","5",
//                    "1102","0","10","7"))
//            assertThat(memory[7]).isEqualTo(5*10)
        }

        @Test
        internal fun `AoC example`() {
            TODO("rewrite this as new instructions")
//            val result: Int = solve(listOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50))
//
//            assertThat(result).isEqualTo(3500)
        }

        fun `solve exercise 1`() {
            val result = solve(csvLines("actualInput.txt"))
            assertThat(result).isEqualTo(0)
        }
    }
}
