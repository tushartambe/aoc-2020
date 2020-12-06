package aoc_2020.day_6

import java.io.File
import java.util.*

fun main() {
    val groupAnswers = mutableListOf(mutableListOf<String>())
    File("src/main/resources/inputs/day_6.txt").readLines().map {
        if (it.isNotEmpty()) {
            val size = groupAnswers.size - 1
            groupAnswers[size].add(it)
        } else {
            groupAnswers.add(mutableListOf())
        }
    }
    println("Part 1 - ${countTotalYesQuestions(groupAnswers)}")
    println("Part 2 - ${countUniqueGroupAnswers(groupAnswers)}")
    println("Part 2 (new approach) - ${countUniqueGroupAnswers1(groupAnswers)}")
}

//Part 1
fun countTotalYesQuestions(groupAnswers: List<List<String>>): Int {
    val uniqueAnswersPerGroup = groupAnswers.map {
        it.joinToString("").toSet().size
    }
    return uniqueAnswersPerGroup.reduce { acc, i -> acc + i }
}

//Part 2
//Older ugly approach
fun countUniqueGroupAnswers(groupAnswers: List<List<String>>): Int {
    val uniqueAnswersPerGroup = groupAnswers.map {
        commonlyAnsweredYesQuestions(it)
    }
    return uniqueAnswersPerGroup.reduce { acc, i -> acc + i }
}

fun commonlyAnsweredYesQuestions(questions: List<String>): Int {
    return if (questions.size == 1) {
        questions.first().length
    } else {
        val chars = questions.map {
            it.toList()
        }
        val commons: MutableList<Char> = ArrayList()
        commons.addAll(chars.first())
        chars.drop(1).forEach {
            commons.retainAll(it)
        }
        commons.size
    }
}

//Part 2 New better approach
fun countUniqueGroupAnswers1(groupAnswers: List<List<String>>): Int {
    val groups = groupAnswers.map {
        it.map {
            it.toSet()
        }
    }
    return groups.map {
        it.reduce { acc, chars ->
            acc.intersect(chars)
        }
    }.sumBy { it.size }
}


