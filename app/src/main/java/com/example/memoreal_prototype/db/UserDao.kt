package com.example.memoreal_prototype.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Update (onConflict = OnConflictStrategy.ABORT)
    suspend fun updateUser(user: User): Int

    @Delete
    suspend fun deleteUser(user: User): Int

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT EXISTS (SELECT 1 FROM user_table WHERE LOWER(username) = LOWER(:username))")
    suspend fun duplicateUser1(username: String): Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM user_table WHERE email = :email)")
    suspend fun duplicateUser2(email: String): Boolean

    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("UPDATE user_table SET first_name = :firstName, last_name = :lastName, middle_initial" +
            " = :mi, contact_number = :contactNumber, birthdate = :birthDate, picture = :picture " +
            "WHERE username = :username")
    suspend fun addUserInfo(firstName: String?, lastName: String?, mi: String?, contactNumber:
    Int?, birthDate: Long?, picture: String?, username: String)

    //for test
    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
    //to reset the primary key
    @Query("DELETE FROM sqlite_sequence WHERE name = 'user_table'")
    suspend fun resetPrimaryKey()
}