package vasyliev.android.simpleusers

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SimpleUsersData(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var firstName: String = "",
    var lastName: String = ""
)