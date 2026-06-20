package com.example.json_data_input.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.json_data_input.dataclass.UserDataClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class UserViewModel: ViewModel() {

    var name by mutableStateOf("")
        private set

    var age by mutableStateOf("")

    var user by mutableStateOf(listOf<UserDataClass?>())
        private set

    var generateJson by mutableStateOf("")
        private set

    var foundUser by mutableStateOf<UserDataClass?>(null)
        private set

    fun onNameChange(newName: String){
        name = newName
    }

    fun onAgeChange(newAge: String){
        age=newAge
    }

    fun addUser(context: Context){
        val newUser = UserDataClass(
            name = name,
            age = age.toIntOrNull() ?: 0
        )

        //Agrega el nuevo usario a la lista
        user = user + newUser

        //Convierte toda la lista a JSON
        generateJson = Gson().toJson(user)

        //Guardar archivo
        saveUsers(context)

        name = ""
        age = ""
    }

    fun searchUser(){
        foundUser = user.find { it?.name == name }
    }

    private fun saveUsers(context: Context){
        val file = File(context.getExternalFilesDir(null), "data.json")
        file.writeText(generateJson)
        Log.d("SAVE_FILE", "Saved in: ${file.absolutePath}")
    }

    fun loadUsers(context: Context){
        val file = File(context.filesDir,"data.json")
        if(file.exists()){
            val json = file.readText()
            val type = object  : TypeToken<List<UserDataClass>>(){}.type
            user = Gson().fromJson(json, type)
            generateJson = json
        }
    }

}