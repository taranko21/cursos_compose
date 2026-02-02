package com.example.room_and_hilt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room_and_hilt.data.dao.UserDao
import com.example.room_and_hilt.data.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dao: UserDao
) : ViewModel() {

    val users = dao.getUsers()

    fun addUser(name: String) {
        viewModelScope.launch {
            dao.insert(User(name = name))
        }
    }
}
