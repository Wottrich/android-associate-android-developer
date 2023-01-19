package wottrich.github.io.pomodorouniverse.home.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import wottrich.github.io.pomodorouniverse.base.models.NotificationModel

@Singleton
class PomodoroNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    init {
        createNotificationChannel(PomodoroNotificationChannels.POMODORO_TIMER_CHANNEL_ID)
    }

    fun updateNotification(notificationModel: NotificationModel) {
        buildNotification(notificationModel)
    }

    fun buildNotification(notificationModel: NotificationModel) {
        val notificationConfiguration = notificationModel.configuration
        val builder =
            NotificationCompat.Builder(context, notificationConfiguration.channelName)
                .setSmallIcon(notificationModel.content.smallIcon)
                .setContentTitle(notificationModel.content.title)
                .setContentText(notificationModel.content.description)
                .setPriority(notificationConfiguration.priority)
                .setOnlyAlertOnce(true)

        NotificationManagerCompat.from(context)
            .notify(notificationModel.id, builder.build())
    }

    fun removeNotification(notificationId: Int) {
        val notificationManager = getNotificationManager()
        notificationManager.cancel(notificationId)
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelId, importance)
            val notificationManager = getNotificationManager()
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun getNotificationManager() =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}