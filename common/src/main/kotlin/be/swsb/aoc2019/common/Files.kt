package be.swsb.aoc2019.common

object Files {
    fun readLines(fileName: String): List<String> = object{}.javaClass.classLoader.getResourceAsStream(fileName)
            .bufferedReader()
            .readLines()
            .filterNot { it.isBlank() }

    inline fun <reified R> readLinesAs(fileName: String, stringConverter: (String) -> R) : List<R> =
        readLines(fileName).map(stringConverter)

    fun csvLines(fileName: String): List<String> = object{}.javaClass.classLoader.getResourceAsStream(fileName)
            .bufferedReader()
            .readLines()
            .filterNot { it.isBlank() }
            .flatMap { it.split(",") }
            .map { it.trim() }

    fun asCsvLines(strings: String): List<String> = strings
            .split(",")
            .map { it.trim() }

    inline fun <reified R> csvLinesAs(fileName: String, stringConverter: (String) -> R) : List<R> =
        csvLines(fileName).map(stringConverter)
}
