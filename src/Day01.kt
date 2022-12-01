fun main() {
    fun part1(input: List<String>): Int {
        var max = 0
        var sum = 0
        for (s in input) {
            if (s != "") {
                sum += s.toInt()
            } else {
                if (sum > max) {
                    max = sum
                }
                sum = 0
            }
        }
        return max
    }

    fun part2(input: List<String>): Int {
        val sums: MutableList<Int> = ArrayList()

        var sum = 0
        for (s in input) {
            if (s != "") {
                sum += s.toInt()
            } else {
                sums.add(sum)
                sum = 0
            }
        }
        sums.add(sum)

        sums.sort()
        sums.reverse()
        return sums.subList(0, 3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
