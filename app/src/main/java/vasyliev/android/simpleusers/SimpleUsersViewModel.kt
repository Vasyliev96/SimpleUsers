package vasyliev.android.simpleusers

import androidx.lifecycle.ViewModel

class SimpleUsersViewModel : ViewModel() {
    val userListLiveData = SimpleUsersRepository.getUsers()
    fun addUser(user: SimpleUsersData) {
        SimpleUsersRepository.addUser(user)
    }
}