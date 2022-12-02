fun main() {
    fun part1(inputs: List<String>): Int {
        var score = 0
        for (hand in inputs) {
            val opponentItem = hand.split(" ")[0]
            val elfItem = hand.split(" ")[1]

            // X = rock
            if (elfItem == "X") {
                score += 1
                // A = rock
                if (opponentItem == "A") {
                    score += 3
                }
                // B = Paper
                if (opponentItem == "B") {
                    score += 0
                }
                // C = Scissor
                if (opponentItem == "C") {
                    score += 6
                }
            }
            // Y = paper
            if (elfItem == "Y") {
                score += 2
                // A = rock
                if (opponentItem == "A") {
                    score += 6
                }
                // B = Paper
                if (opponentItem == "B") {
                    score += 3
                }
                // C = Scissor
                if (opponentItem == "C") {
                    score += 0
                }
            }
            // Z = siccor
            if (elfItem == "Z") {
                score += 3
                // A = rock
                if (opponentItem == "A") {
                    score += 0
                }
                // B = Paper
                if (opponentItem == "B") {
                    score += 6
                }
                // C = Scissor
                if (opponentItem == "C") {
                    score += 3
                }
            }

        }
        return score
    }

    fun part2(inputs: List<String>): Int {
        var score = 0
        for (hand in inputs) {
            val opponentItem = hand.split(" ")[0]
            val elfItem = hand.split(" ")[1]

            // A = rock
            if (opponentItem == "A"){
                if (elfItem == "X") {
                    //loose ==> siccor
                    score += 3
                    score += 0
                }
                if (elfItem == "Y") {
                    //draw ==> rock
                    score += 1
                    score += 3
                }
                if (elfItem == "Z") {
                    //win ==> paper
                    score += 2
                    score += 6
                }
            }
            // B = Paper
            if (opponentItem == "B"){
                if (elfItem == "X") {
                    //loose ==> rock
                    score += 1
                    score += 0
                }
                if (elfItem == "Y") {
                    //draw ==> paper
                    score += 2
                    score += 3
                }
                if (elfItem == "Z") {
                    //win ==> siccor
                    score += 3
                    score += 6
                }
            }

            // C = Scissor
            if (opponentItem == "C"){
                if (elfItem == "X") {
                    //loose ==> paper
                    score += 2
                    score += 0
                }
                if (elfItem == "Y") {
                    //draw ==> siccor
                    score += 3
                    score += 3
                }
                if (elfItem == "Z") {
                    //win ==> rock
                    score += 1
                    score += 6
                }
            }
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    check(part1(input) == 13221)
    println(part1(input))
    check(part2(input) == 13131)
    println(part2(input))
}
