package aoc_2020.day_5

import java.io.File

fun main() {
    val input = File("src/main/resources/inputs/day_5.txt").readLines()
    val seatIds = input.map {
        findSeatIdUsingRecursion(it)
    }.sorted()

    println("Part 1 - ${seatIds.last()}")
    println(seatIds)
    println("Part 2 - ${findMissingSeatId(seatIds)}")
}

//older and ugly Approach
fun findSeatId(seat: String): Int {
    val rowCharacters = seat.take(7)
    val columnCharacters = seat.takeLast(3)
    var rowRange = (0..127).toList()
    var columnRange = (0..7).toList()

    rowCharacters.forEach {
        rowRange = remainingRowSeats(it, rowRange)
    }

    columnCharacters.forEach {
        columnRange = remainingColumnSeats(it, columnRange)
    }

    return (rowRange.first() * 8) + columnRange.first()
}

fun remainingRowSeats(char: Char, range: List<Int>): List<Int> {
    return if (char == 'F') {
        range.take(range.size / 2)
    } else {
        range.takeLast(range.size / 2)
    }
}

fun remainingColumnSeats(char: Char, range: List<Int>): List<Int> {
    return if (char == 'L') {
        range.take(range.size / 2)
    } else {
        range.takeLast(range.size / 2)
    }
}

//Little Better Approach
fun findSeatIdUsingRecursion(seat: String): Int {
    val row = findFinalSeatByPartition(seat.take(7), (0..127).toList())
    val column = findFinalSeatByPartition(seat.takeLast(3), (0..7).toList())
    return row * 8 + column
}

fun findFinalSeatByPartition(chars: String, range: List<Int>): Int {
    return if (range.size == 1) {
        range.first()
    } else {
        if (chars.first() == 'F' || chars.first() == 'L') {
            findFinalSeatByPartition(chars.drop(1), range.take(range.size / 2))
        } else {
            findFinalSeatByPartition(chars.drop(1), range.takeLast(range.size / 2))
        }
    }
}

//Part 2
fun findMissingSeatId(seatIds: List<Int>): Int {
    val previousSeatOfMissingSeat = seatIds.dropLast(1).filterIndexed { index, i ->
        i + 1 < seatIds[index + 1]
    }.first()
    return previousSeatOfMissingSeat + 1
}