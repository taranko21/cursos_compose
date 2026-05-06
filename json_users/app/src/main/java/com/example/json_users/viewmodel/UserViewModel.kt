package com.example.json_users.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.json_users.dataclass.UserDataClass

class UserViewModel: ViewModel() {
    private val repository = UserRepository()

    var user by mutableStateOf<UserDataClass?>(null)
        private set

    fun loaduser(){
        user = repository.getUser()
    }
}