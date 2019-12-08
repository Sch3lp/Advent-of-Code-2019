package be.swsb.aoc2019

import be.swsb.aoc2019.common.Files.readLinesAs
import be.swsb.aoc2019.Day1_1.multipleModulesFuelCounterUpper
import be.swsb.aoc2019.Day1_1.singleModuleFuelCounterUpper
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
            val modules = readLinesAs("actualInput.txt", String::toInt)
            val result = multipleModulesFuelCounterUpper(modules)

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

    @Nested
    inner class Exercise2 {
        @Test
        fun `solve exercise 2`() {
            val modules = readLinesAs("actualInput.txt", String::toInt)
            assertThat(Day1_2.sumOfAllFuelsPerModule(modules))
                    .isEqualTo(5068454)
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
