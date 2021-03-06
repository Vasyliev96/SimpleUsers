package vasyliev.android.simpleusers.ui.edituserpage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import vasyliev.android.simpleusers.db.SimpleUsersData
import vasyliev.android.simpleusers.repository.SimpleUsersRepository
import java.util.*

class SimpleUsersDetailViewModel : ViewModel() {
    private var isUserNew = true
    private val userIdLiveData = MutableLiveData<UUID>()
    var userLiveData: LiveData<SimpleUsersData?> =
        Transformations.switchMap(userIdLiveData) { userId ->
            SimpleUsersRepository.getUser(userId)
        }

    fun loadUser(userId: UUID) {
        userIdLiveData.value = userId
    }

    fun saveUser(user: SimpleUsersData) {
        SimpleUsersRepository.updateUser(user)
    }

    fun deleteUser(user: SimpleUsersData) {
        SimpleUsersRepository.deleteUser(user)
    }

    fun addUser(user: SimpleUsersData) {
        SimpleUsersRepository.addUser(user)
    }

    fun setUserNotNew() {
        isUserNew = false
    }

    fun isUserNew(): Boolean {
        return isUserNew
    }
}
