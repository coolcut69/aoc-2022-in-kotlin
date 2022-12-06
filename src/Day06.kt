fun main() {

    fun part1(input: String): Int {
        for (i in 0..input.length - 4) {
            val message = input.substring(i..i + 3)
            val groupBy: Map<String, List<String>> = message.chunked(1).groupBy { it.uppercase() }
            if (groupBy.size == 4){
//                println(message)
                return i + 4
            }
        }
        return 0
    }

    fun part2(inputs: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")

    check(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 7)
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)
//    check(part2(testInput) == 0)

    val input = readInput("Day06")
    println(part1(input.first()))
//    check(part1(input) == 0)

//    println(part2(input))
//    check(part2(input) == 0)
}


