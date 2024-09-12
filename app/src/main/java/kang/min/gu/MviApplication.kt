package kang.min.gu

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MviApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}