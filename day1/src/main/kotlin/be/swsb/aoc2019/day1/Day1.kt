package be.swsb.aoc2019.day1

object Day1_1 {
    fun readFile(fileName: String): List<Int> = this::class.java.getResourceAsStream(fileName)
            .bufferedReader()
            .readLines()
            .filterNot { it.isBlank() }
            .map { Integer.parseInt(it) }

    fun multipleModulesFuelCounterUpper(modules: List<Int>): Int = modules.sumBy { singleModuleFuelCounterUpper(it) }
    fun singleModuleFuelCounterUpper(moduleMass: Int): Int = Math.floorDiv(moduleMass, 3) - 2
}

object Day1_2 {
    fun sumOfAllFuelsPerModule(modules: List<ModuleMass>): FuelRequirement = fuelPerModule(modules).sum()
    fun fuelPerModule(modules: List<ModuleMass>): List<FuelRequirement> = modules.map { singleModuleFuelCounterUpper(it) }
    fun singleModuleFuelCounterUpper(moduleMass: ModuleMass): FuelRequirement {
        return loop(0, formula((moduleMass)))
    }

    private tailrec fun loop(acc: FuelRequirement, fuel: FuelRequirement): FuelRequirement {
        if (fuel <= 0) return acc
        return loop(acc + fuel, formula(fuel))
    }

    private fun formula(moduleMass: ModuleMass): FuelRequirement = Math.floorDiv(moduleMass, 3) - 2
}

typealias ModuleMass = Int
typealias FuelRequirement = Int
