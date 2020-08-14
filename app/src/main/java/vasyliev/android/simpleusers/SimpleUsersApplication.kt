package vasyliev.android.simpleusers

import android.app.Application

class SimpleUsersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SimpleUsersRepository.initialize(this)
    }
}