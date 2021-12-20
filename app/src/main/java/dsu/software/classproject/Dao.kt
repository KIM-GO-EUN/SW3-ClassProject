package dsu.software.classproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity)

    @Query("SELECT userPw FROM user WHERE userId = :userId")
    fun loginQuerying(userId: String): String

    @Query("SELECT * FROM user")
    fun getAllValues(): List<UserEntity>
}

@Dao
interface VisitedDao {
    @Query("SELECT * FROM visited WHERE visitedUser = :visitedUser")
    fun getValues(visitedUser: String): List<VisitedEntity>

    @Query("SELECT * FROM visited")
    fun getAllValues(): List<VisitedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(visitedEntity: VisitedEntity)

    @Query("UPDATE visited set visitedUser = :visitedUser, visitedDate = :visitedDate, visitedLoc = :visitedLoc, visitedTime = :visitedTime WHERE idx = :idx")
    fun update(idx: Int, visitedUser: String, visitedDate: String, visitedLoc: Int, visitedTime: String)

    @Query("DELETE FROM visited WHERE idx = :idx")
    fun delete(idx: Int)
}

@Dao
interface BeaconDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(beaconEntity: BeaconEntity)

    @Query("SELECT * FROM beacon")
    fun getAllValues(): List<BeaconEntity>

    @Query("SELECT * FROM beacon WHERE beaconId = :beaconId")
    fun getValues(beaconId: Int): List<BeaconEntity>
}
