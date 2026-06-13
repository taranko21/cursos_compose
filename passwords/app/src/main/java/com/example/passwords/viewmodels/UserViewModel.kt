package com.example.passwords.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.passwords.dataclass.UserDataClass
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

class UserViewModel: ViewModel() {
    var website by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var user by mutableStateOf<Map<String, UserDataClass>>(emptyMap())
        private set

    var generateJson by mutableStateOf("")
        private set

    var foundUser by mutableStateOf<UserDataClass?>(null)
        private set

    var message by mutableStateOf("")
        private set

    fun onWebsiteChange(newWebsite: String){
        website = newWebsite
    }
    fun newEmailChange(newEmail: String){
        email = newEmail
    }
    fun newPasswordChange(newPassword: String){
        password = newPassword
    }

    fun addUser(context: Context){

        if(
            website.isBlank() ||
            email.isBlank() ||
            password.isBlank()
        ){
            message = "Missing data!"
            return
        }

        if(
            user.containsKey(website)
        ){
            message = "Website already exist!"
            website = ""
            email = ""
            password = ""
            return
        }

        val newUser = UserDataClass(
            email = email,
            password = password
        )
        user = user + (website to newUser)
        generateJson = GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(user)
        saveUser(context)
        website = ""
        email = ""
        password = ""
        message = "Data saved!"
        foundUser = null
    }
    fun searchUser(){
        foundUser = user[website]

        message = if(foundUser == null){
            "Website not found"
        } else {
            ""
        }
    }
    private fun saveUser(context: Context){
        val file = File(context.getExternalFilesDir(null),"data.json")
        file.writeText(generateJson)
        Log.d("SAVE_FILE", "Save in: ${file.absolutePath}")
    }
    fun loadUser(context: Context){
        val file = File(context.getExternalFilesDir(null),"data.json")
        if(file.exists()){
            val json = file.readText()
            val type = object : TypeToken<Map<String, UserDataClass>>(){}.type
            user = Gson().fromJson(json, type)
            generateJson = json
        }
    }
    fun deleteUser(website: String, context: Context) {

        user = user - website

        generateJson = GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(user)


        foundUser = null
        message = "Data deleted!"
        saveUser(context)

    }
    fun randomPassword(){
        val letters = ('a'.. 'z') + ('A'..'Z')
        val numbers = ('0'..'9')
        val symbols = listOf(
            '!', '@', '#', '$', '%', '&', '?'
        )
        val allCharacters =
            letters + numbers + symbols

        val randomPassword = (1..15)
            .map {
                allCharacters.random()
            }
            .joinToString("")

        password = randomPassword
    }
}