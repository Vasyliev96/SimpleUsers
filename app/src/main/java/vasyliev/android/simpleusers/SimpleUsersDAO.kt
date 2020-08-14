package vasyliev.android.simpleusers

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface SimpleUsersDAO {
    @Query("SELECT * FROM simpleusersdata")
    fun getUsers(): LiveData<List<SimpleUsersData>>

    @Query("SELECT * FROM simpleusersdata WHERE id=(:id)")
    fun getUser(id: UUID): LiveData<SimpleUsersData?>

    @Update
    fun updateUser(simpleUser: SimpleUsersData)

    @Insert
    fun addUser(user: SimpleUsersData)

    @Delete
    fun deleteUser(simpleUser: SimpleUsersData)
}