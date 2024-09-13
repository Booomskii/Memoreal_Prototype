package com.example.memoreal_prototype.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_table",
    indices = [Index(value = ["username"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="first_name")
    var firstName:String? = null,
    @ColumnInfo(name="last_name")
    var lastName:String? = null,
    @ColumnInfo(name="middle_initial")
    var mi:String? = null,
    @ColumnInfo(name="username")
    var username:String,
    @ColumnInfo(name="password")
    var password:String,
    @ColumnInfo(name="contact_number")
    var contactNumber:String? = null,
    @ColumnInfo(name="email")
    var email:String,
    @ColumnInfo(name="birthdate")
    var birthDate:String? = null,
    @ColumnInfo(name="picture")
    var picture:String? = null
)

@Entity(
    tableName = "obituary_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"]/*,
         onDelete = ForeignKey.CASCADE
         (kani siya kay mo delete sa uban tables if deleted ni
         siya)*/)
    ]
)
data class Obituary(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="obituary_id")
    var obituaryID:Int,
    @ColumnInfo(name="user_id")
    var userID:Int,
    @ColumnInfo(name="biography")
    var biography:String,
    @ColumnInfo(name="obituary_name")
    var obituaryName:String,
    @ColumnInfo(name="date_of_birth")
    var dateOfBirth:String,
    @ColumnInfo(name="date_of_death")
    var dateOfDeath:String,
    @ColumnInfo(name="key_events")
    var keyEvents:String,
    @ColumnInfo(name="picture")
    var picture:String? = null,
    @ColumnInfo(name="achievements")
    var achievements:String? = null,
    @ColumnInfo(name="favorite_quotes")
    var favoriteQuotes:String? = null,
    @ColumnInfo(name="creation_date")
    var creationDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name="last_modified")
    var lastModified: Long
)