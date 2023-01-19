package wottrich.github.io.pomodorouniverse.base

import android.content.Context
import androidx.annotation.StringRes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StringResProvider {
    fun getString(@StringRes stringRes: Int): String
}

class StringResProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
): StringResProvider {
    override fun getString(stringRes: Int): String {
        return context.getString(stringRes)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class StringResProviderModule {
    @Binds
    abstract fun bindsStringResProvider(
        stringResProviderImpl: StringResProviderImpl
    ): StringResProvider
}