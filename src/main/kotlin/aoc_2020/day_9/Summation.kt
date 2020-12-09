package aoc_2020.day_9

import java.io.File

fun main() {
    val input = File("src/main/resources/inputs/day_9.txt").readLines().map {
        it.toLong()
    }
    println("Part - 1 : ${findNumberDoNotFollowPreamble(input, 25)}")
    println("Part - 2 : ${sumOfMaxAndMinOfInvalidRange(input, 25)}")
}

fun findNumberDoNotFollowPreamble(numbers: List<Long>, preambleNumber: Int): Long {
    val numbersDoesNotFollow = numbers.windowed(preambleNumber + 1, 1, false).first {
        !doesAnyTwoNumberSumTo(it.last(), it.dropLast(1))
    }
    return numbersDoesNotFollow.last()
}

fun doesAnyTwoNumberSumTo(sum: Long, numbers: List<Long>): Boolean {
    val allNumbers = numbers.toSet()
    return allNumbers.any { sum - it in allNumbers }
}

fun sumOfMaxAndMinOfInvalidRange(numbers: List<Long>, preambleNumber: Int): Long {
    val sum = findNumberDoNotFollowPreamble(numbers, preambleNumber)
    val numberTillFirstInvalidPreamble = numbers.takeWhile { it != sum }
    val subRange = findSubRangeSumTo(sum, numberTillFirstInvalidPreamble)
    val max = subRange?.maxOrNull() ?: 0
    val min = subRange?.minOrNull() ?: 0
    return max + min
}

fun findSubRangeSumTo(sum: Long, numbers: List<Long>): List<Long>? {
    return numbers.indices.mapNotNull { start ->
        numbers.indices.drop(start + 1).reversed().mapNotNull { end ->
            val subList = numbers.subList(start, end)
            subList.takeIf { it.sum() == sum }
        }.firstOrNull()
    }.firstOrNull()
}