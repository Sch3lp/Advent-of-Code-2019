package be.swsb.aoc2019

import be.swsb.aoc2019.InputMode.Immediate
import be.swsb.aoc2019.InputMode.PositionMode
import be.swsb.aoc2019.common.Files.csvLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day5Test {


    @Nested
    inner class ParsingIntoInstructions {
        /*
        given : "1001", "100", "-1", "3", "1002", ..., 3

        parse "1001"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Operation.addition(, ImmediateMod)

        parse "100"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Operation.addition(100)

        parse "-1"
        memory = "1001", "100", "-1", "3", "1002", ..., 3
        currentInstruction = Operation.addition(100),Value(-1)

        parse "3"
        memory = "1001", "100", "-1", "2", "1002", ..., 3
        currentInstruction = Operation.addition(100),Value(-1),DestinationAt(3)

        parse "1002"
        memory = "1001", "100", "-1", "2", "1002", ..., 3
        currentInstruction = Multiplication
         */
        @Test
        internal fun `partiallyExecute | gradually parses into an Addition and executes it`() {
            val memory = memory(1001, 5, -1, 3, 1002, 2)
            val memoryAfterAddition = memory(1001, 5, -1, 1, 1002, 2).pointerAt(5)

            assertThat(partiallyExecute(memory, null))
                    .isEqualTo(memory.pointerAt(1) to Operation.addition(PositionMode, Immediate))

            assertThat(partiallyExecute(memory.increasePointer(), Operation.addition(PositionMode, Immediate)))
                    .isEqualTo(memoryAfterAddition to null)
        }

        @Test
        internal fun `partiallyExecute | gradually parses into a Multiplication and executes it`() {
            val memory = memory(1002, 5, -4, 3, 1002, 2)
            val memoryAfterMultiplication = memory(1002, 5, -4, -8, 1002, 2).pointerAt(5)

            assertThat(partiallyExecute(memory, null))
                    .isEqualTo(memory.pointerAt(1) to Operation.multiplication(PositionMode, Immediate))

            assertThat(partiallyExecute(memory.increasePointer(), Operation.multiplication(PositionMode, Immediate)))
                    .isEqualTo(memoryAfterMultiplication to null)
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
