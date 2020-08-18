package vasyliev.android.simpleusers

import android.app.Application
import vasyliev.android.simpleusers.repository.SimpleUsersRepository

class SimpleUsersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SimpleUsersRepository.initialize(this)
    }
}