package vasyliev.android.simpleusers.ui.homepage.viewmodel

import androidx.lifecycle.ViewModel
import vasyliev.android.simpleusers.repository.SimpleUsersRepository

class SimpleUsersViewModel : ViewModel() {

    val userListLiveData = SimpleUsersRepository.getUsers()
}