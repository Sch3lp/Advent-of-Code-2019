package be.swsb.aoc2019.common

import kotlin.system.measureTimeMillis

object Metrics {

    inline fun <reified R : Any> measure(measuredThing: String, block: () -> R): R {
        println("Measuring $measuredThing")
        var result: R? = null
        val elapsedTime = measureTimeMillis { result = block() }
        println("$measuredThing took $elapsedTime millis to complete")
        return result!!
    }
}

inline fun <reified R : Any> measure(measuredThing: String, block: () -> R) = Metrics.measure(measuredThing, block)
