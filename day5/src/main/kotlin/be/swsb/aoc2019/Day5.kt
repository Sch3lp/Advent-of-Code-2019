package be.swsb.aoc2019


fun solve(intCodes: List<String>): Int {
    val memory: Memory = parseIntoMemory(intCodes.map { it.toInt() })
    partiallyExecute(memory, null)
//    val instructions = parseInput(intCodes)

//    var intCode = IntCodes(intCodes)
//    instructions.forEach { instruction ->
//        intCode = intCode.execute(instruction)
//    }
//    return intCode.single()
    return 0
}

enum class InputMode {
    PositionMode,
    Immediate
}

fun partiallyExecute(memory: Memory, currentInstruction: Instruction?): Pair<Memory, Instruction?> {
    // memory -> state
    // gradually build up an instruction
    // when it's _complete_, execute it and apply it to memory

    val (updatedInstruction, updatedMemory) = if (currentInstruction != null) {
        when (currentInstruction) {
            is Instruction.Addition -> {
                currentInstruction.loadParam1(memory)
                .loadParam2(memory.increasePointer()) to memory.increasePointer().increasePointer()
            }
            else -> throw IllegalStateException("TODO other instructions")
        }
    } else {
        Instruction.instructionFromIntCode(memory[memory.pointer]) to memory.increasePointer()
    }
    return updatedMemory to updatedInstruction
}


