package aoc_2020.day_1

import java.io.File

fun main(args: Array<String>) {
    val input = File("src/main/resources/inputs/day_1.txt").readLines().map {
        it.toInt()
    }
    val targetedSum = 2020

    findMultiplicationOfTwoEnrties(targetedSum, input)
    findMultiplicationOfThreeEnrties(targetedSum, input)
}

private fun findMultiplicationOfTwoEnrties(targetNumber: Int, reportEntries: List<Int>) {
    val complement = findComplements(targetNumber, reportEntries).first()
    val number = targetNumber - complement
    println("Multiplication : ${complement * number}")

//  Older code [Still works]
//    reportEntries.map { entry ->
//        val find = reportEntries.find { targetNumber - it == entry }
//        if (find != null) {
//            println("Items : $entry , $find")
//            println("Multiplication : ${entry * find}")
//        }
//    }
}

private fun findMultiplicationOfThreeEnrties(targetNumber: Int, reportEntries: List<Int>) {
    for (firstNumber in reportEntries) {
        val target = targetNumber - firstNumber
        val complements = findComplements(target, reportEntries)
        if (complements.isNotEmpty()) {
            val secondNumber = complements.first()
            println("Multiplication : ${firstNumber * secondNumber * (target - secondNumber)}")
            break
        }
    }

//    OLDER code [Still works]
//    reportEntries.map { entry ->
//        val others = reportEntries.drop(1)
//        others.map { second ->
//            val last = others.drop(1)
//
//            val find = last.find {
//                2020 - it == entry + second
//            }
//
//            if (find != null) {
//                println("Items : $entry ,$second, $find")
//                println("Multiplication : ${entry * find * second}")
//            }
//        }
//    }
}


private fun findComplements(targetNumber: Int, reportEntries: List<Int>): List<Int> {
    return reportEntries.map { entry ->
        reportEntries.find { targetNumber - it == entry }
    }.filterNotNull()
}
