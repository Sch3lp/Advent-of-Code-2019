package be.swsb.aoc2019.common

object Common {
    fun readLines(fileName: String): List<String> = object{}.javaClass.classLoader.getResourceAsStream(fileName)
            .bufferedReader()
            .readLines()
            .filterNot { it.isBlank() }

    inline fun <reified R> parseLinesAs(fileName: String, stringConverter: (String) -> R) : List<R> =
        readLines(fileName).map(stringConverter)
}
