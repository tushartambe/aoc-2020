package aoc_2020.day_2

import java.io.File

// Problem Statement : https://adventofcode.com/2020/day/2

fun main(args: Array<String>) {
    val input = File("src/main/resources/inputs/day_2.txt").readLines().map {
        val range = it.split(" ").first()
        val letter = it.split(" ")[1].dropLast(1)
        val value = it.split(" ").last()

        val minCount = range.split("-").first().toInt()
        val maxCount = range.split("-").last().toInt()
        Password(letter, minCount, maxCount, value)
    }

    val validPasswordsByCountPolicy = input.count { it.isValidByCountPolicy() }
    println("Part 1 - Valid Passwords : $validPasswordsByCountPolicy")

    val validPasswordsByPositionPolicy = input.count { it.isValidByPositionPolicy() }
    println("Part 2 - Valid Passwords : $validPasswordsByPositionPolicy")
}

class Password(
    private val givenLetter: String,
    private val firstNumber: Int,
    private val secondNumber: Int,
    private val value: String
) {
    //For first part of problem
    fun isValidByCountPolicy(): Boolean {
        val count = value.count { it.toString() == givenLetter }
        return count in firstNumber..secondNumber
    }

    //For second part of problem
    fun isValidByPositionPolicy(): Boolean {
        val charAtFirstPosition = value[firstNumber - 1]
        val charAtLastPosition = value[secondNumber - 1]
        val count = listOf(charAtFirstPosition, charAtLastPosition).count { it.toString() == givenLetter }
        return count == 1
    }
}