fun main() {
    fun parseFileSystem(inputs: List<String>): Directory {
        val rootDirectory = Directory("/", null)
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
                    currentDir.addDirectory(directory)
                } else {
                    val size = action.split(" ")[0].toInt()
                    val fileName = action.split(" ")[1]
                    File(fileName, size)
                    currentDir.addFile(File(fileName, size))
                }
            }
        }
        return rootDirectory
    }

    fun part1(inputs: List<String>): Int {
        val rootDirectory = parseFileSystem(inputs)
        return rootDirectory.getDirectories(false).filter { it.getSize() <= 10_0000 }.sumOf { it.getSize() }
    }

    fun part2(inputs: List<String>): Int {
        val rootDirectory = parseFileSystem(inputs)
        val requiredDiskspace = 30_000_000 - (70_000_000 - rootDirectory.getSize())
        return rootDirectory.getDirectories(true).filter { it.getSize() > requiredDiskspace }.minOf { it.getSize() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95_437)
    check(part2(testInput) == 24_933_642)

    val input = readInput("Day07")
//    println(part1(input))
    check(part1(input) == 1_723_892)
//    println(part2(input))
    check(part2(input) == 8_474_158)
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

    fun getDirectory(name: String): Directory {
        return directories.find { directory: Directory -> directory.name == name }!!
    }

    fun getSize(): Int {
        return files.sumOf { it.size } + directories.sumOf { it.getSize() }
    }

    fun getDirectories(includeRoot: Boolean): List<Directory> {
        return when (includeRoot) {
            true -> concatenate(listOf(this), directories, directories.flatMap { it.getDirectories(false) })
            false -> concatenate(directories, directories.flatMap { it.getDirectories(false) })
        }
    }
}

fun <T> concatenate(vararg lists: List<T>): List<T> {
    return listOf(*lists).flatten()
}

data class File(val name: String, val size: Int)
