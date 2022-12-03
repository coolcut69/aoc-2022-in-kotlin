fun main() {
    val scores = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun part1(inputs: List<String>): Int {
        var score = 0
        for (rucksack in inputs) {
            val chunked = rucksack.chunked(rucksack.length / 2)
            val firstCompartment = chunked.first()
            val secondsCompartment = chunked.last()

            val intersect = firstCompartment.toList().intersect(secondsCompartment.toList().toSet())
            score += scores.indexOf(intersect.first()) + 1
        }
        return score
    }

    fun part2(inputs: List<String>): Int {
        var score = 0
        for (rucksack in inputs.chunked(3)) {
            val intersect =
                rucksack.get(0).toList().intersect(rucksack.get(1).toList()).intersect(rucksack.get(2).toList())
            score += scores.indexOf(intersect.first()) + 1
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    check(part1(input) == 8298)
    println(part2(input))
    check(part2(input) == 2708)
}


