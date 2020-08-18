package vasyliev.android.simpleusers.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SimpleUsersData::class], version = 1, exportSchema = false)
@TypeConverters(SimpleUsersTC::class)
abstract class SimpleUsersDB : RoomDatabase() {
    abstract fun simpleUsersDAO(): SimpleUsersDAO
}
