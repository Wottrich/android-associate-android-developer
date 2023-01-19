package wottrich.github.io.pomodorouniverse.base

import kotlin.random.Random

object UuidGenerator {
    private val lowercaseChars = ('a'..'z')
    private val uppercaseChars = ('A'..'Z')
    private val numberChars = ('0'..'9')
    private val defaultCharPool: List<Char> = lowercaseChars + uppercaseChars + numberChars
    private val defaultPoolSize = (1..64)

    fun getRandomUuid(
        charPool: List<Char> = defaultCharPool,
        poolSize: IntRange = defaultPoolSize
    ): String {
        return poolSize
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun getIntRandomUuid(): Int {
        return getRandomUuid(
            numberChars.toList(),
            (1..8)
        ).toInt()
    }
}