package wottrich.github.io.pomodorouniverse.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import wottrich.github.io.pomodorouniverse.base.UuidGenerator
import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroPlayerStatus

@Entity(
    tableName = "timer_type",
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = PomodoroTimer::class,
            parentColumns = arrayOf("uuid"),
            childColumns = arrayOf("parent_uuid")
        )
    ]
)
data class TimerType(
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    val uuid: String = UuidGenerator.getRandomUuid(),
    @ColumnInfo(name = "parent_uuid")
    val parentUuid: String,
    @ColumnInfo(name = "status")
    val status: PomodoroPlayerStatus,
    @ColumnInfo(name = "total_time")
    val totalTime: Long,
    @ColumnInfo(name = "current_time")
    val currentTime: Long,
    @ColumnInfo(name = "type")
    val type: PomodoroType = PomodoroType.WORK,
    @ColumnInfo(name = "is_finished")
    val isFinished: Boolean = false
)