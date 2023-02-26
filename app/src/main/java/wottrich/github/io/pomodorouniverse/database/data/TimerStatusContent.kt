package wottrich.github.io.pomodorouniverse.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import wottrich.github.io.pomodorouniverse.base.UuidGenerator

@Entity(
    tableName = "timer_status_content",
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = TimerType::class,
            parentColumns = arrayOf("uuid"),
            childColumns = arrayOf("parent_uuid")
        )
    ]
)
data class TimerStatusContent(
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    val uuid: String = UuidGenerator.getRandomUuid(),
    @ColumnInfo(name = "parent_uuid")
    val parentUuid: String,
    @ColumnInfo(name = "content")
    val content: String
)