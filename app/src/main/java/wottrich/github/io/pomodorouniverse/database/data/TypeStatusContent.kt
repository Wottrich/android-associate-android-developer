package wottrich.github.io.pomodorouniverse.database.data

import androidx.room.Embedded
import androidx.room.Relation

data class TypeStatusContent(
    @Embedded val timerType: TimerType,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "parent_uuid"
    )
    val timerStatusContent: TimerStatusContent
)