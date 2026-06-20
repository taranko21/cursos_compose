package com.example.datos_room.vmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.datos_room.data.dao.UserDao

class UserViewModelFactory (private val dao: UserDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(dao) as T

    }
}