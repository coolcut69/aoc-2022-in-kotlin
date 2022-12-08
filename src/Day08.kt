fun main() {

    fun visibleFromTop(tree: Tree, trees: MutableList<Tree>): Boolean {
        val filter = trees.filter { top -> tree.y == top.y && tree.x > top.x }
        val maxOf = filter.maxOf { it.height }
        return tree.height > maxOf
    }

    fun visibleFromBottom(tree: Tree, trees: MutableList<Tree>): Boolean {
        val filter = trees.filter { bottom -> tree.y == bottom.y && tree.x < bottom.x }
        val maxOf = filter.maxOf { it.height }
        return tree.height > maxOf
    }

    fun visibleFromLeft(tree: Tree, trees: MutableList<Tree>): Boolean {
        val filter = trees.filter { left -> tree.x == left.x && tree.y > left.y }
        val maxOf = filter.maxOf { it.height }
        return tree.height > maxOf
    }

    fun visibleFromRight(tree: Tree, trees: MutableList<Tree>): Boolean {
        val filter = trees.filter { right -> tree.x == right.x && tree.y < right.y }
        val maxOf = filter.maxOf { it.height }
        return tree.height > maxOf
    }

    fun scenicScoreLeft(tree: Tree, trees: MutableList<Tree>): Int {
        val filter = trees.filter { right -> tree.x == right.x && tree.y > right.y }
        if (filter[0].height == tree.height) return 1
        var i = 0
        for (t in filter.reversed()) {
            if (t.height <= tree.height){
                i++
            }
            if (t.height == tree.height){
                i++
                return i
            }
        }
        return i
    }

    fun scenicScoreRight(tree: Tree, trees: MutableList<Tree>): Int {
        val filter = trees.filter { right -> tree.x == right.x && tree.y < right.y }
        if (filter[0].height == tree.height) return 1
        var i = 0
        for (t in filter) {
            if (t.height <= tree.height){
                i++
            }
            if (t.height > tree.height){
                i++
                return i
            }
        }
        return i
    }

    fun scenicScoreTop(tree: Tree, trees: MutableList<Tree>): Int {
        val filter = trees.filter { right -> tree.y == right.y && tree.x > right.x }
        if (filter[0].height == tree.height) return 1

        var i = 0
        for (t in filter.reversed()) {
            if (t.height <= tree.height){
                i++
            }
            if (t.height > tree.height){
                i++
                return i
            }
        }
        return i
    }

    fun scenicScoreBottom(tree: Tree, trees: MutableList<Tree>): Int {
        val filter = trees.filter { right -> tree.y == right.y && tree.x < right.x }
        if (filter[0].height == tree.height) return 1

        var i = 0
        for (t in filter) {
            if (t.height <= tree.height){
                i++
            }
            if (t.height > tree.height){
                i++
                return i
            }
        }
        return i
    }

    fun createForest(inputs: List<String>): MutableList<Tree> {
        val trees: MutableList<Tree> = ArrayList()
        for ((i, row) in inputs.withIndex()) {
            for ((j, char) in row.toList().withIndex()) {
                trees.add(Tree(i, j, char.toString().toInt()))
            }
        }
        return trees
    }

    fun part1(inputs: List<String>): Int {
        val forest: MutableList<Tree> = createForest(inputs)
        val maxX = forest.maxOf { tree -> tree.x } - 1
        val maxY = forest.maxOf { tree -> tree.y } - 1

        val visibleTrees: MutableSet<Tree> = HashSet()

        // check left to right and right to left
        for (i in (1..maxX)) {
            val leftToRight = forest.filter { tree -> tree.x == i && tree.y != 0 && tree.y != inputs.size - 1 }
            for (tree in leftToRight) {
                if (visibleFromLeft(tree, forest)) {
                    visibleTrees.add(tree)
                }
                if (visibleFromRight(tree, forest)) {
                    visibleTrees.add(tree)
                }
            }
        }
        for (i in (1..maxY)) {
            val topToBottom = forest.filter { tree -> tree.y == i && tree.x != 0 && tree.x != inputs.size - 1 }
            for (tree in topToBottom) {
                if (visibleFromTop(tree, forest)) {
                    visibleTrees.add(tree)
                }
                if (visibleFromBottom(tree, forest)) {
                    visibleTrees.add(tree)
                }
            }

        }

        return visibleTrees.size + forest.size - (maxX * maxY)
    }

    fun part2(inputs: List<String>): Int {
        val forest: MutableList<Tree> = createForest(inputs)
        val maxX = forest.maxOf { tree -> tree.x } - 1
        val maxY = forest.maxOf { tree -> tree.y } - 1

        val scenicScores: MutableSet<Int> = HashSet()

        // check left to right and right to left
        for (i in (1..maxX)) {
            val leftToRight = forest.filter { tree -> tree.x == i && tree.y != 0 && tree.y != inputs.size - 1 }
            for (tree in leftToRight) {
                val scenicScore = scenicScoreTop(tree, forest) *
                        scenicScoreLeft(tree, forest) *
                        scenicScoreBottom(tree, forest) *
                        scenicScoreRight(tree, forest)
                scenicScores.add(scenicScore)
            }
        }

         val score = scenicScoreTop(Tree(3,2,5), forest) *
                scenicScoreLeft(Tree(3,2,5), forest) *
                scenicScoreBottom(Tree(3,2,5), forest) *
                scenicScoreRight(Tree(3,2,5), forest)

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    check(part1(input) == 1538)
//    println(part2(input))
//    check(part2(input) == 0)
}

data class Tree(
    val x: Int,
    val y: Int,
    val height: Int
)
