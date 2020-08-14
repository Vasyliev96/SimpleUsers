package vasyliev.android.simpleusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class SimpleUsersDetailViewModel : ViewModel() {
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
}
