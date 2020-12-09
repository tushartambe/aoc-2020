package aoc_2020.day_8

import java.io.File

fun main() {
    val input = File("src/main/resources/inputs/day_8.txt").readLines().mapIndexed { index, s ->
        Instruction(index, s)
    }

    println("Part 1 - ${findAccValue(input).first}")
    println("Part 2 - ${findAccValueByChangingInstruction(input)}")

}

data class Instruction(
    val id: Int,
    var instruction: String,
    var executedTimes: Int = 0
)

fun findAccValue(instructions: List<Instruction>): Pair<Int, Boolean> {
    val newInstructions = instructions.map {
        it.executedTimes = 0
        it
    }
    var accumulator = 0
    var currentInstruction = 0
    val isLoopPresent: Boolean
    while (true) {
        if (currentInstruction >= newInstructions.size) {
            return Pair(accumulator, false)
        } else {
            val instruction = newInstructions[currentInstruction]
            if (instruction.executedTimes > 0) {
                isLoopPresent = true
                break
            } else {
                newInstructions[currentInstruction].executedTimes += 1
                val actions = instruction.instruction.split(" ")
                when (actions.first()) {
                    "acc" -> {
                        accumulator += actions.last().toInt()
                        currentInstruction++
                    }
                    "jmp" -> {
                        currentInstruction += actions.last().toInt()
                    }
                    else -> currentInstruction++
                }
            }
        }
    }

    return Pair(accumulator, isLoopPresent)
}

fun findAccValueByChangingInstruction(instructions: List<Instruction>): Int {
    var result = 0
    var index = 0
    while (index < instructions.size) {
        val instruction = instructions[index]
        val oldInstruction = instruction.instruction
        val actions = instruction.instruction.split(" ")

        if (actions.first() == "jmp" && actions.last().toInt() != 0) {
            instruction.instruction = "nop ${actions.last()}"

            val findAccValue = findAccValue(instructions)
            if (!findAccValue.second) {
                result = findAccValue.first
                break
            } else {
                instruction.instruction = oldInstruction
            }
        }

        if (actions.first() == "nop" && actions.last().toInt() != 0) {
            instruction.instruction = "jmp ${actions.last()}"

            val findAccValue = findAccValue(instructions)
            if (!findAccValue.second) {
                result = findAccValue.first
                break
            } else {
                instruction.instruction = oldInstruction
            }
        }
        index++
    }
    return result
}