package wottrich.github.io.pomodorouniverse.base

import java.util.Locale
import java.util.concurrent.TimeUnit

object StringFormatter {
    fun formatElapsedTime(time: Long): String {
        return String.format(
            Locale.getDefault(), "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time) % TimeUnit.DAYS.toHours(1),
            TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1)
        )
    }
}