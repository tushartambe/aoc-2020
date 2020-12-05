package aoc_2020.day_4

import java.io.File

fun main() {
    val passports = parseInputIntoPassports()
    val requiredFields = listOf("ecl", "pid", "eyr", "hcl", "byr", "iyr", "hgt")
    val optionalFields = listOf("cid")
    println("Part 1 - ${countValidPassport(passports, requiredFields)}")
    println("Part 2 - ${countValidPassportByDataValidation(passports, requiredFields)}")
}

fun parseInputIntoPassports(): MutableList<String> {
    val passports = mutableListOf<String>()
    File("src/main/resources/inputs/day_4.txt").readLines().map {
        if (passports.isEmpty()) {
            passports.add(it)
        } else {
            if (it != "") {
                val size = passports.size - 1
                passports[size] += " $it"
            } else {
                passports.add(it)
            }
        }
    }

    return passports
}

//Part 1
fun countValidPassport(passports: List<String>, requiredFields: List<String>): Int {
    return passports.count {
        val passport = it.trim()
        val presentFieldsInPassport = passport.split(" ").map { it.split(":")[0] }
        presentFieldsInPassport.containsAll(requiredFields)
    }
}

//Part 2
fun countValidPassportByDataValidation(
    passports: List<String>,
    requiredFields: List<String>
): Int {
    return passports.count {
        val passport = it.trim()
        val presentFieldsInPassport = passport.split(" ").map { it.split(":")[0] }

        if (presentFieldsInPassport.containsAll(requiredFields)) {
            val count = passport.split(" ").count { fields ->
                val keyAndVal = fields.split(":")
                isPassportValid(keyAndVal.first(), keyAndVal.last())
            }
            if ("cid" in presentFieldsInPassport) {
                count == 8
            } else {
                count == 7
            }
        } else {
            false
        }
    }
}

fun isPassportValid(field: String, value: String): Boolean {
    return when (field) {
        "byr" -> {
            value.toInt() in 1920..2002
        }
        "iyr" -> {
            value.toInt() in 2010..2020
        }
        "eyr" -> {
            value.toInt() in 2020..2030
        }
        "hgt" -> {
            if (value.length > 2) {
                val unit = value.takeLast(2)
                val height = value.dropLast(2).toInt()
                if (unit == "cm")
                    height in 150..193
                else
                    height in 59..76
            } else {
                false
            }
        }
        "hcl" -> {
            val hexValue = value.drop(1)
            value.first() == '#' && hexValue.toLong(16) <= "ffffff".toLong(16)
        }
        "ecl" -> {
            val allowedColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            value in allowedColors
        }
        "pid" -> {
            value.length == 9 && value.toIntOrNull() != null
        }
        else -> true
    }
}
