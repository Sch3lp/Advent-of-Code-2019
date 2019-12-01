package be.swsb.aoc2019.day1

import be.swsb.aoc2019.day1.Day1_1.multipleModulesFuelCounterUpper
import be.swsb.aoc2019.day1.Day1_1.readFile
import be.swsb.aoc2019.day1.Day1_1.singleModuleFuelCounterUpper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day1Test {

    @Nested
    inner class Exercise1 {
        @Test
        fun `solve exercise 1`() {
            val result = multipleModulesFuelCounterUpper(readFile("actualInput.txt"))

            assertThat(result).isEqualTo(3380880)
        }

        @Test
        fun `Fuel Counter-upper can calculate the fuel needed for multiple modules`() {
            val fuelNeeded: Int = multipleModulesFuelCounterUpper(listOf(12, 12))

            assertThat(fuelNeeded).isEqualTo(4)
        }

        @Test
        fun `Fuel Counter-upper can calculate the fuel needed for a single module`() {
            val fuelNeeded: Int = singleModuleFuelCounterUpper(12)

            assertThat(fuelNeeded).isEqualTo(2)
        }
    }

    @Test
    fun `Can read file line by line`() {
        val actual = readFile("input.txt")

        assertThat(actual).containsExactly(12, 24)
    }

    @Nested
    inner class Exercise2 {
        @Test
        fun `solve exercise 2`() {
            assertThat(Day1_2.sumOfAllFuelsPerModule(readFile("actualInput.txt"))).isEqualTo(5068454)
        }

        @Test
        fun `can sum all fuel requirements of all modules`() {
            assertThat(Day1_2.sumOfAllFuelsPerModule(listOf(1969, 21))).isEqualTo(971)
        }

        @Test
        fun `can calculate fuel per module`() {
            assertThat(Day1_2.fuelPerModule(listOf(1969, 21))).containsExactly(966, 5)
        }

        @Test
        fun `singleModuleFuelCounterUpper Day1 2`() {
            assertThat(Day1_2.singleModuleFuelCounterUpper(1969)).isEqualTo(966)
        }
    }
}
