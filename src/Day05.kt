import kotlin.collections.ArrayDeque

fun main() {

    fun part1(inputs: List<String>, stacks: MutableMap<Int, ArrayDeque<String>>): String {
        for (action in inputs) {
            val fromPosition = action.indexOf("from")
            val movePosition = action.indexOf("move")
            val toPosition = action.indexOf("to")

            val numberOfCrates = action.substring(movePosition + 5, fromPosition - 1).toInt()
            val source = action.substring(fromPosition + 5, toPosition -1).toInt()
            val destination = action.substring(toPosition + 3).toInt()

            val sourceStack = stacks[source]
            val destinationStack = stacks[destination]

            for (i in 1..numberOfCrates) {
                destinationStack?.addLast(sourceStack?.removeLast()!!)
            }

        }
        return stacks.map { it.value.last() }.joinToString("")
    }

    fun part2(inputs: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val stacks = mutableMapOf<Int, ArrayDeque<String>>()
    stacks[1] = ArrayDeque(listOf("Z", "N"))
    stacks[2] = ArrayDeque(listOf("M", "C", "D"))
    stacks[3] = ArrayDeque(listOf("P"))

    check(part1(testInput, stacks) == "CMZ")
//    check(part2(testInput, stacks) == 0)

    val input = readInput("Day05")
    stacks[1] = ArrayDeque(listOf("T","D","W","Z","V","P"))
    stacks[2] = ArrayDeque(listOf("L","S","W","V","F","J","D"))
    stacks[3] = ArrayDeque(listOf("Z","M","L","S","V","T","B","H"))
    stacks[4] = ArrayDeque(listOf("R","S","J"))
    stacks[5] = ArrayDeque(listOf("C","Z","B","G","F","M","L","W"))
    stacks[6] = ArrayDeque(listOf("Q","W","V","H","Z","R","G","B"))
    stacks[7] = ArrayDeque(listOf("V","J","P","C","B","D","N"))
    stacks[8] = ArrayDeque(listOf("P","T","B","Q"))
    stacks[9] = ArrayDeque(listOf("H","G","Z","R","C"))

    println(part1(input, stacks))
//    check(part1(input, stacks) == "")
//    println(part2(input))
//    check(part2(input) == 0)
}
