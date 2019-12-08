package be.swsb.aoc2019

object Day1_1 {
    fun multipleModulesFuelCounterUpper(modules: List<ModuleMass>): FuelRequirement = modules.sumBy { singleModuleFuelCounterUpper(it) }
    fun singleModuleFuelCounterUpper(moduleMass: ModuleMass): FuelRequirement = Math.floorDiv(moduleMass, 3) - 2
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
