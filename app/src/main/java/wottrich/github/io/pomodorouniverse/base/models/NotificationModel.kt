package wottrich.github.io.pomodorouniverse.base.models

import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import wottrich.github.io.pomodorouniverse.R
import wottrich.github.io.pomodorouniverse.base.UuidGenerator

data class NotificationModel(
    val id: Int = UuidGenerator.getIntRandomUuid(),
    val content: NotificationContent = NotificationContent(),
    val configuration: NotificationConfiguration = NotificationConfiguration(),
)

data class NotificationContent(
    val title: String = "",
    val description: String = "",
    @DrawableRes val smallIcon: Int = R.drawable.ic_round_timer
)

data class NotificationConfiguration(
    val channelName: String = "",
    val priority: Int = NotificationCompat.PRIORITY_DEFAULT,
)