package dsu.software.classproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val userId: String,
    val userPw: String,
    val userName: String,
    val userBirthday: String,
    val userHome: String,
)

@Entity(tableName = "beacon")
data class BeaconEntity(
    @PrimaryKey val beaconId: Int,
    val location: String
)

@Entity(tableName = "visited")
data class VisitedEntity(
    @PrimaryKey val idx: Int,
    val visitedUser: String,
    val visitedDate: String,
    val visitedLoc: Int,
    val visitedTime: String
)
