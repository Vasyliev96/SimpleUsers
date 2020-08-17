package vasyliev.android.simpleusers

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "simple-users-database"

object SimpleUsersRepository {

    private lateinit var simpleUsersDB: SimpleUsersDB
    private lateinit var simpleUsersDAO: SimpleUsersDAO
    private val executor = Executors.newSingleThreadExecutor()

    fun initialize(context: Context) {
        simpleUsersDB = Room.databaseBuilder(
            context.applicationContext,
            SimpleUsersDB::class.java,
            DATABASE_NAME
        ).build()
        simpleUsersDAO = simpleUsersDB.simpleUsersDAO()
    }

    fun getUsers(): LiveData<List<SimpleUsersData>> = simpleUsersDAO.getUsers()
    fun getUser(id: UUID): LiveData<SimpleUsersData?> = simpleUsersDAO.getUser(id)
    fun updateUser(user: SimpleUsersData) {
        executor.execute {
            simpleUsersDAO.updateUser(user)
        }
    }

    fun addUser(user: SimpleUsersData) {
        executor.execute {
            simpleUsersDAO.addUser(user)
        }
    }

    fun deleteUser(user: SimpleUsersData) {
        executor.execute {
            simpleUsersDAO.deleteUser(user)
        }
    }
}