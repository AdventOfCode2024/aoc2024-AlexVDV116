fun main() {
    // Read the test input and input files
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    fun parseInput(input: List<String>): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val blankLineIndex = input.indexOfFirst { it.isBlank() }

        // Extract the first part of the input and parse into pairs of integers
        val rules = input.take(blankLineIndex)
            .map { it.split("|").let { (a, b) -> a.toInt() to b.toInt() } }
        // Extract the second part of the input and parse into lists of integers
        val updates = input.drop(blankLineIndex + 1)
            .map { it.split(",").map(String::toInt) }

        return rules to updates
    }

    // A rule consists of two integers, a and b, that define the printing order of page numbers
    // 11 | 29 means that page 11 should be printed before page 29
    // An update consists of a list of integers that represent the printing order of page numbers
    // An update matches a rule if the pages are printed in the order defined by all the rules
    fun doesUpdateMatchRules(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        // Create a list to store the rules that apply to the update\
        val applicableRules = mutableListOf<Pair<Int, Int>>()

        // Check if the rules applies to the update by checking if the integers in the rules are in the update
        rules.forEach { rule ->
            if (rule.first in update && rule.second in update) {
                applicableRules.add(rule)
            }
        }

        // If the update does not match any rules, return false
        applicableRules.forEach { rule ->
            val (a, b) = rule
            val aIndex = update.indexOf(a)
            val bIndex = update.indexOf(b)
            if (aIndex > bIndex) return false
        }
        return true
    }

    // Function that returns the middle element of a list
    fun middleElement(list: List<Int>): Int {
        return list[list.size / 2]
    }

    fun part1(input: List<String>): Int {
        val (rules, updates) = parseInput(input)
        println(rules)
        println(updates)

        // Create a list to store the updates that match all the rules
        val matchingUpdates = mutableListOf<List<Int>>()
        updates.forEach { update ->
            // If the update matches all the rules, save the update in the list
            if (doesUpdateMatchRules(update, rules)) {
                matchingUpdates.add(update)
            }
        }

        // For all elements in the matching updates, get the middle element and return the sum of all the middle elements
        return matchingUpdates.map(::middleElement).sum()
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = parseInput(input)

        // Function to fix an update by iterating through the rules and swapping numbers
        fun fixUpdate(update: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
            val fixedUpdate = update.toMutableList()
            var swapped: Boolean

            // do while loop to iterate through the rules and swap the numbers in the update
            // until no more swaps are needed which means the update is fixed
            do {
                swapped = false
                for ((a, b) in rules) {
                    // Get the index of a and b in the update
                    val aIndex = fixedUpdate.indexOf(a)
                    val bIndex = fixedUpdate.indexOf(b)

                    // Check if both a and b exist in the update and if it follows the rule after swapping
                    if (aIndex != -1 && bIndex != -1 && aIndex > bIndex) {
                        // Remove 'a' and insert it before 'b' - swappping the numbers
                        fixedUpdate.removeAt(aIndex)
                        fixedUpdate.add(bIndex, a)

                        swapped = true
                    }
                }
            } while (swapped) // Keep iterating until no more swaps are needed

            return fixedUpdate
        }

        // Filter updates that do not match the rules, fix them, and sum up their middle elements
        return updates.filter { !doesUpdateMatchRules(it, rules) }
            .map { fixUpdate(it, rules) }
            .map(::middleElement)
            .sum()
    }

    // Print solutions
    println(part1(input))
    println(part2(input))
}