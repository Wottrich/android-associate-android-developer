package wottrich.github.io.pomodorouniverse.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import wottrich.github.io.pomodorouniverse.database.AppDatabase
import wottrich.github.io.pomodorouniverse.database.dao.PomodoroTimerDao
import wottrich.github.io.pomodorouniverse.database.dao.TimerStatusContentDao
import wottrich.github.io.pomodorouniverse.database.dao.TimerTypeDao

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun providerPomodoroTimerDao(appDatabase: AppDatabase): PomodoroTimerDao {
        return appDatabase.pomodoroTimerDao()
    }

    @Provides
    fun providerTimerTypeDao(appDatabase: AppDatabase): TimerTypeDao {
        return appDatabase.timerTypeDao()
    }

    @Provides
    fun providerTimerStatusContentDao(appDatabase: AppDatabase): TimerStatusContentDao {
        return appDatabase.timerStatusContentDao()
    }

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }
}