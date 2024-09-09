package com.example.memoreal_prototype.db

import androidx.room.ColumnInfo
import androidx.room.Entity
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