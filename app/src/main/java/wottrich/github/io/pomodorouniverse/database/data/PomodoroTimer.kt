package wottrich.github.io.pomodorouniverse.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import wottrich.github.io.pomodorouniverse.base.StringFormatter
import wottrich.github.io.pomodorouniverse.base.UuidGenerator
import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType

@Entity(tableName = "pomodoro_timer")
data class PomodoroTimer(
    @ColumnInfo(name = "uuid")
    @PrimaryKey
    val uuid: String = UuidGenerator.getRandomUuid(),
    @ColumnInfo(name = "notification_uuid")
    val notificationUuid: Int = UuidGenerator.getIntRandomUuid(),
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: PomodoroType = PomodoroType.WORK,
    @ColumnInfo(name = "current_time")
    val currentTime: Long,
) {
    fun getFormattedDate(): String {
        return StringFormatter.formatElapsedTime(currentTime)
    }
}