package wottrich.github.io.pomodorouniverse.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import wottrich.github.io.pomodorouniverse.di.AppModule

class PomodoroUniverseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PomodoroUniverseApplication)
            androidLogger(Level.DEBUG)
            modules(AppModule.modules)
        }
    }

}