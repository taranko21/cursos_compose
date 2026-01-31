package com.example.datos_room.vmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datos_room.data.dao.UserDao
import com.example.datos_room.data.entity.UsersEntity
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao): ViewModel() {

    val users = userDao.getUsers()

    fun addUser(name: String) {
        viewModelScope.launch {
            userDao.insert(UsersEntity(name = name))
        }
    }
}