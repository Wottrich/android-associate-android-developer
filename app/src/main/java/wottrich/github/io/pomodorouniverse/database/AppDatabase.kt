package wottrich.github.io.pomodorouniverse.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import wottrich.github.io.pomodorouniverse.database.dao.PomodoroTimerDao
import wottrich.github.io.pomodorouniverse.database.dao.TimerStatusContentDao
import wottrich.github.io.pomodorouniverse.database.dao.TimerTypeDao

abstract class AppDatabase : RoomDatabase() {

    abstract fun pomodoroTimerDao(): PomodoroTimerDao
    abstract fun timerTypeDao(): TimerTypeDao
    abstract fun timerStatusContentDao(): TimerStatusContentDao

    companion object {
        private const val DATABASE_NAME = "pomodoroUniverseDatabase2023"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }

}