package vasyliev.android.simpleusers

import androidx.room.TypeConverter
import java.util.*

class SimpleUsersTC {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}
