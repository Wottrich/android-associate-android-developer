package wottrich.github.io.pomodorouniverse.home.presentation.models

import wottrich.github.io.pomodorouniverse.base.models.NotificationModel
import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType

fun NotificationModel.updateDescription(description: String): NotificationModel {
    return copy(
        content = content.copy(
            description = description
        )
    )
}

fun NotificationModel.updateTitle(title: String): NotificationModel {
    return copy(
        content = content.copy(
            title = title
        )
    )
}

fun NotificationModel.updateContentByPomodoroType(pomodoroType: PomodoroType) {

}