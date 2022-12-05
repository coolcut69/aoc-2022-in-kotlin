fun main() {

    fun parseAction(actionString: String): Action {
        val fromPosition = actionString.indexOf("from")
        val movePosition = actionString.indexOf("move")
        val toPosition = actionString.indexOf("to")

        val numberOfCrates = actionString.substring(movePosition + 5, fromPosition - 1).toInt()
        val source = actionString.substring(fromPosition + 5, toPosition - 1).toInt()
        val destination = actionString.substring(toPosition + 3).toInt()
        return Action(numberOfCrates, source, destination)
    }

    fun part1(inputs: List<String>, stacks: MutableMap<Int, ArrayDeque<String>>): String {
        for (actionString in inputs) {
            val action = parseAction(actionString)
            val sourceStack = stacks[action.source]
            val destinationStack = stacks[action.destination]

            repeat(action.numberOfCrates) {
                destinationStack?.addLast(sourceStack?.removeLast()!!)
            }
        }
        return stacks.map { it.value.last() }.joinToString("")
    }

    fun part2(inputs: List<String>, stacks: MutableMap<Int, ArrayDeque<String>>): String {
        for (actionString in inputs) {
            val action = parseAction(actionString)
            val sourceStack = stacks[action.source]
            val destinationStack = stacks[action.destination]

            val moved = mutableListOf<String>()
            repeat(action.numberOfCrates) {
                sourceStack?.removeLast()?.let { it1 -> moved.add(it1) }
            }
            moved.reverse()
            moved.forEach {
                destinationStack?.addLast(it)
            }
        }
        return stacks.map { it.value.last() }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val stacks = mutableMapOf<Int, ArrayDeque<String>>()
    stacks[1] = ArrayDeque(listOf("Z", "N"))
    stacks[2] = ArrayDeque(listOf("M", "C", "D"))
    stacks[3] = ArrayDeque(listOf("P"))

    check(part1(testInput, stacks) == "CMZ")

    stacks[1] = ArrayDeque(listOf("Z", "N"))
    stacks[2] = ArrayDeque(listOf("M", "C", "D"))
    stacks[3] = ArrayDeque(listOf("P"))
    check(part2(testInput, stacks) == "MCD")

    val input = readInput("Day05")
    stacks[1] = ArrayDeque(listOf("T", "D", "W", "Z", "V", "P"))
    stacks[2] = ArrayDeque(listOf("L", "S", "W", "V", "F", "J", "D"))
    stacks[3] = ArrayDeque(listOf("Z", "M", "L", "S", "V", "T", "B", "H"))
    stacks[4] = ArrayDeque(listOf("R", "S", "J"))
    stacks[5] = ArrayDeque(listOf("C", "Z", "B", "G", "F", "M", "L", "W"))
    stacks[6] = ArrayDeque(listOf("Q", "W", "V", "H", "Z", "R", "G", "B"))
    stacks[7] = ArrayDeque(listOf("V", "J", "P", "C", "B", "D", "N"))
    stacks[8] = ArrayDeque(listOf("P", "T", "B", "Q"))
    stacks[9] = ArrayDeque(listOf("H", "G", "Z", "R", "C"))

//    println(part1(input, stacks))
    check(part1(input, stacks) == "TLFGBZHCN")

    stacks[1] = ArrayDeque(listOf("T", "D", "W", "Z", "V", "P"))
    stacks[2] = ArrayDeque(listOf("L", "S", "W", "V", "F", "J", "D"))
    stacks[3] = ArrayDeque(listOf("Z", "M", "L", "S", "V", "T", "B", "H"))
    stacks[4] = ArrayDeque(listOf("R", "S", "J"))
    stacks[5] = ArrayDeque(listOf("C", "Z", "B", "G", "F", "M", "L", "W"))
    stacks[6] = ArrayDeque(listOf("Q", "W", "V", "H", "Z", "R", "G", "B"))
    stacks[7] = ArrayDeque(listOf("V", "J", "P", "C", "B", "D", "N"))
    stacks[8] = ArrayDeque(listOf("P", "T", "B", "Q"))
    stacks[9] = ArrayDeque(listOf("H", "G", "Z", "R", "C"))
//    println(part2(input, stacks))
    check(part2(input, stacks) == "QRQFHFWCL")
}

data class Action(
    val numberOfCrates: Int,
    val source: Int,
    val destination: Int
)
