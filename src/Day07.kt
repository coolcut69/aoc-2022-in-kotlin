fun main() {
    fun part1(inputs: List<String>): Int {
        val rootDirectory = Directory("/", null)
        val dirList : MutableList<Directory> = ArrayList()
        var currentDir = rootDirectory
        for (action in inputs) {
            if (action.startsWith("$")) {
                // command
                when (action) {
                    "$ ls" -> continue
                    "$ cd .." -> currentDir = currentDir.parent!!
                    else -> {
                        val newDir = action.substringAfter("$ cd ")
                        if (newDir != "/") {
                            currentDir = currentDir.getDirectory(newDir)
                        }
                    }
                }
            } else {
                if (action.startsWith("dir")) {
                    val dirName = action.substringAfter("dir ")
                    val directory = Directory(dirName, currentDir)
                    dirList.add(directory)
                    currentDir.addDirectory(directory)
                } else {
                    val size = action.split(" ")[0].toInt()
                    val fileName = action.split(" ")[1]
                    File(fileName, size)
                    currentDir.addFile(File(fileName, size))
                }
            }

        }

        return rootDirectory.getDirectories().filter { it.getSize() <= 100000 }.sumOf { it.getSize() }
    }

    fun part2(inputs: List<String>): Int {
        var score = 0
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
//    check(part2(testInput) == 0)

    val input = readInput("Day07")
    println(part1(input))
    check(part1(input) == 1723892)
//    check(part2(input) == 0)
//    println(part2(input))
}

data class Directory(val name: String, val parent: Directory?) {
    private val files: MutableList<File> = ArrayList()
    private val directories: MutableList<Directory> = ArrayList()

    fun addFile(file: File) {
        files.add(file)
    }

    fun addDirectory(directory: Directory) {
        directories.add(directory)
    }

    fun getDirectory(name: String) : Directory {
        return directories.find { directory: Directory -> directory.name == name  }!!
    }

    fun getSize() : Int {
        return files.sumOf { it.size } + directories.sumOf { it.getSize() }
    }

    fun getDirectories(): List<Directory> {
        return concatenate(directories, directories.flatMap { it.getDirectories() })
    }
}

fun <T> concatenate(vararg lists: List<T>): List<T> {
    return listOf(*lists).flatten()
}

data class File(val name: String, val size: Int)
