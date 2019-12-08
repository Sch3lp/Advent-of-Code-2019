package be.swsb.aoc2019

import be.swsb.aoc2019.common.Common.csvLinesAs
import be.swsb.aoc2019.day2.Instruction.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.lang.IllegalArgumentException


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day3Test {

    @Nested
    inner class ParsingIntoOpcodeStatements {
        @Test
        internal fun `fromAListOfMax4Ints | cannot parse from a list of more than 4 Intcodes`() {
            assertThatExceptionOfType(IllegalArgumentException::class.java)
                    .isThrownBy { Instruction.fromAListOfMax4Ints(listOf(1, 2, 3, 4, 5)) }
        }

        @Test
        internal fun `fromAListOfMax4Ints | an unknown Opcode is parsed to Noop`() {
            val opcodeStatement = Instruction.fromAListOfMax4Ints(listOf(3, 2, 3, 4))

            assertThat(opcodeStatement).isEqualTo(Noop)
        }

        @Test
        internal fun `fromAListOfMax4Ints | can parse into Addition when Opcode is 1`() {
            val opcodeStatement = Instruction.fromAListOfMax4Ints(listOf(1, 2, 3, 4))

            assertThat(opcodeStatement).isEqualTo(Addition(2, 3, 4))
        }

        @Test
        internal fun `fromAListOfMax4Ints | can parse into Multiplication when Opcode is 2`() {
            val opcodeStatement = Instruction.fromAListOfMax4Ints(listOf(2, 2, 3, 4))

            assertThat(opcodeStatement).isEqualTo(Multiplication(2, 3, 4))
        }

        @Test
        internal fun `fromAListOfMax4Ints | can parse into Halt when Opcode is 99`() {
            val opcodeStatement = Instruction.fromAListOfMax4Ints(listOf(99, 2, 3))

            assertThat(opcodeStatement).isEqualTo(Halt)
        }

        @Test
        internal fun `parseInput | can parse input into OpcodeStatements`() {
            val result: List<Instruction> = parseInput(listOf(1, 9, 10, 3, 2, 22, 33, 44, 99, 2))

            assertThat(result).containsExactly(
                    Addition(9, 10, 3),
                    Multiplication(22, 33, 44),
                    Halt
            )
        }
    }

    @Nested
    inner class ExecutingInstructions {
        @Test
        internal fun `execute | applying Addition, applies addition on the given positions and returns an updated Intcodes`() {
            val intCodes = IntCodes(listOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50))

            val actual = intCodes.execute(Addition(9, 10, 3))

            assertThat(actual).isEqualTo(IntCodes(listOf(1, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50)))
        }

        @Test
        internal fun `execute | applying Multiplication, applies multiplication on the given positions and returns an updated Intcodes`() {
            val intCodes = IntCodes(listOf(1, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50))

            val actual = intCodes.execute(Multiplication(3, 11, 0))

            assertThat(actual).isEqualTo(IntCodes(listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50)))
        }

        @Test
        internal fun `execute | applying Halt, returns Intcodes with just position 0`() {
            val intCodes = IntCodes(listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50))

            val actual = intCodes.execute(Halt)

            assertThat(actual).isEqualTo(IntCodes(listOf(3500)))
        }

        @Test
        internal fun `execute | applying Noop, returns the same Intcodes`() {
            val intCodes = IntCodes(listOf(0,2,3,4))

            val actual = intCodes.execute(Noop)

            assertThat(actual).isEqualTo(IntCodes(listOf(0,2,3,4)))
        }
    }

    @Nested
    inner class Exercise1 {

        @Test
        internal fun `AoC example`() {
            val result: Int = solve(listOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50))

            assertThat(result).isEqualTo(3500)
        }

        @Test
        fun `solve exercise 1`() {
            val result = solve(csvLinesAs("actualInput.txt", String::toInt))
            assertThat(result).isEqualTo(4090689)
        }
    }


    @Nested
    inner class Exercise2 {
        @Test
        fun `solve exercise 2`() {
            val toModify = csvLinesAs("actualInput.txt", String::toInt)
            for (noun in 0..99) {
                for (verb in 0..99) {
                    val modifiedIntCodes = toModify.toMutableList().apply {
                        this[1] = noun
                        this[2] = verb
                    }
                    if (solve(modifiedIntCodes) == 19690720) {
                        println("Noun: $noun\nVerb: $verb\nSolution: ${(100 * noun) + verb}")
                    }
                }
            }
        }
    }
}
