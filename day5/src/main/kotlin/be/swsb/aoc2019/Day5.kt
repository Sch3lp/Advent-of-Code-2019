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

fun partiallyExecute(memory: Memory, currentInstruction: Instruction?): Pair<Memory, Instruction?> {
    // memory -> state
    // gradually build up an instruction
    // when it's _complete_, execute it and apply it to memory

    val (updatedInstruction, updatedMemory) = if (currentInstruction != null) {
        when (currentInstruction) {
            is Operation -> {
                completeAndExecuteOperation(currentInstruction, memory)
            }
            else -> throw IllegalStateException("TODO other instructions")
        }
    } else {
        instructionFromIntCode(memory[memory.pointer]) to memory.increasePointer()
    }
    return updatedMemory to updatedInstruction
}

private fun completeAndExecuteOperation(currentInstruction: Operation, memory: Memory): Pair<Operation?, Memory> {
    val (instruction, newMemory) = completeOperation(currentInstruction, memory)
    return null to instruction.execute(newMemory)
}

private fun completeOperation(currentInstruction: Operation, memory: Memory) =
        currentInstruction
                .loadParam1(memory)
                .loadParam2(memory.increasePointer())
                .loadDestination(memory.increasePointer().increasePointer()) to memory.increasePointer().increasePointer().increasePointer()


