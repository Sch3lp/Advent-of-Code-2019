package be.swsb.aoc2019.common

object Common {
    fun readFile(fileName: String): List<String> = object{}.javaClass.classLoader.getResourceAsStream(fileName)
            .bufferedReader()
            .readLines()
            .filterNot { it.isBlank() }
}
