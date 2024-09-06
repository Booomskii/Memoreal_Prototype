package com.example.memoreal_prototype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreal_prototype.db.User
import com.example.memoreal_prototype.db.UserDao
import kotlinx.coroutines.launch

class UserViewModel(private val dao: UserDao): ViewModel() {

    val users = dao.getAllUsers()

    suspend fun clearAllUsers() {
        dao.deleteAllUsers()
    }
    suspend fun resetPK() {
        dao.resetPrimaryKey()
    }

    fun insertUser(user: User)= viewModelScope.launch{
        dao.insertUser(user)
    }

    fun updateUser(user: User)= viewModelScope.launch{
        dao.updateUser(user)
    }

    fun deleteUser(user: User)= viewModelScope.launch{
        dao.deleteUser(user)
    }
}