package com.example.json_data_input.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.json_data_input.dataclass.UserDataClass
import com.google.gson.Gson

class UserViewModel: ViewModel() {

    var name by mutableStateOf("")
        private set

    var age by mutableStateOf("")

    var user by mutableStateOf(listOf<UserDataClass?>())
        private set

    var generateJson by mutableStateOf("")
        private set

    fun onNameChange(newName: String){
        name = newName
    }

    fun onAgeChange(newAge: String){
        age=newAge
    }

    fun addUser(){
        val newUser = UserDataClass(
            name = name,
            age = age.toIntOrNull() ?: 0
        )

        //Agrega el nuevo usario a la lista
        user = user + newUser

        //Convierte toda la lista a JSON
        generateJson = Gson().toJson(user)

        name = ""
        age = ""
    }

}