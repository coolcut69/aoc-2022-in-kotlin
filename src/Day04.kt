fun main() {
    fun part1(inputs: List<String>): Int {
        inputs.asSequence()
        val sections: List<Sections> =
            inputs.map { it.split(",") }
                .map { Sections(Section.from(it.first()), Section.from(it.last())) }
        return sections.count { it.fullyOverlap() }
    }

    fun part2(inputs: List<String>): Int {
        val sections: List<Sections> =
            inputs.map { it.split(",") }
                .map { Sections(Section.from(it.first()), Section.from(it.last())) }
        return sections.count { it.partialOverlap() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    check(part1(input) == 475)
    println(part2(input))
    check(part2(input) == 825)
}


data class Section(val start: Int, val end: Int) {
    companion object {
        fun from(s: String): Section {
            val split = s.split("-")
            return Section(split.get(0).toInt(), split.get(1).toInt())
        }
    }
}

data class Sections(val first: Section, val second: Section) {
    fun fullyOverlap(): Boolean {
        val firstList = (first.start..first.end).toList()
        val secondList = (second.start..second.end).toList()
        val intersect = firstList.intersect(secondList)
        return firstList.size == intersect.size || secondList.size == intersect.size
    }

    fun partialOverlap(): Boolean {
        val firstList = (first.start..first.end).toList()
        val secondList = (second.start..second.end).toList()
        val intersect = firstList.intersect(secondList)
        return intersect.size > 0
    }
}
