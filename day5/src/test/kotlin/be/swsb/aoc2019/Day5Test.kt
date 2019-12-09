package be.swsb.aoc2019

import be.swsb.aoc2019.Instruction.*
import be.swsb.aoc2019.Parameter.ImmediateMode
import be.swsb.aoc2019.Parameter.PositionMode
import be.swsb.aoc2019.common.Files.csvLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day5Test {

    //Questions:
    // it says "and so on", so are there an infinite amount of parameters possible?
    // or am I just silly and it depends merely on the sort of instruction in the 2 right most digits?

    @Nested
    inner class ParsingIntoIntCodes {
        @Test
        internal fun `parseIntoIntCodes | Strings get turned into IntCodes`() {
            val intCodes = parseIntoIntCodes(listOf("1001", "1", "-1", "4", "0"))

            assertThat(intCodes).containsExactly(
                    IntCode("1001"),
                    IntCode("1"),
                    IntCode("-1"),
                    IntCode("4"),
                    IntCode("0")
            )
        }
    }

    @Nested
    inner class ParsingIntoInstructions {

        /*
        given : "1001", "100", "-1", "3", "1002", ..., 3

        parse "1001"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Addition

        parse "100"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Addition(ValueAt(100))

        parse "-1"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Addition(ValueAt(100),Just(-1))

        parse "3"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Addition(ValueAt(100),Just(-1),DestinationAt(3))

        parse "1002"
        memory = "1001", "100", "-1", "2", "1002", ..., 3
        currentInstruction = Multiplication
         */

        @Test
        internal fun `parseInput | gradually parses into an Instruction and executes it`() {
            val memory = intCodes("1001", "5", "-1", "3", "1002", "2")
            val memoryAfterAddition = intCodes("1001", "5", "-1", "1", "1002", "2")

            assertThat(partiallyExecute(memory, null))
                    .isEqualTo(memory to Addition())

            assertThat(partiallyExecute(memory, Addition()))
                    .isEqualTo(memory to Addition(PositionMode(5), ImmediateMode(-1)))

            assertThat(partiallyExecute(memory, Addition(PositionMode(5), ImmediateMode(-1))))
                    .isEqualTo(memory to Addition(PositionMode(5), ImmediateMode(-1), PositionMode(3)))

            assertThat(partiallyExecute(memory, Addition(PositionMode(5), ImmediateMode(-1), PositionMode(3))))
                    .isEqualTo(memoryAfterAddition to Multiplication())
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
