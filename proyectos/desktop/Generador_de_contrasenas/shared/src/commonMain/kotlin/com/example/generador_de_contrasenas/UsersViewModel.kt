package com.example.generador_de_contrasenas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

class UsersViewModel {
    var website by mutableStateOf("")
        private set
    var email by  mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var users by mutableStateOf<Map<String, UsersDataClass>>(emptyMap())
        private set
    var generateJson by mutableStateOf("")
    private set

    var foundUser by mutableStateOf<UsersDataClass?>(null)
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

    private val dataFile = File(
        System.getProperty("user.home"),
        ".generador_contrasena/data.json"
    )

    fun addUser(){
        if(
            website.isBlank() ||
            email.isBlank() ||
            password.isBlank()
        ){
            message = "Missing data"
            return
        }
        if(users.containsKey((website))){
            message = "Website already exist!"
            website = ""
            email = ""
            password = ""
            return
        }
        val newUser = UsersDataClass(
            email = email,
            password = password
        )
        users = users + (website to newUser)
        generateJson = GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(users)
        saveUser()
        website = ""
        email=""
        password = ""
        message="User saved!"
        foundUser = null
    }
    fun searchUser(){
        foundUser = users[website]

        if (foundUser != null) {
            email = foundUser!!.email
            password = foundUser!!.password
            message = ""
        } else {
            message = "Website not found"
        }
    }
    private fun saveUser(){
        dataFile.parentFile?.mkdirs()
        dataFile.writeText(generateJson)
        println("Save in ${dataFile.absolutePath}")

    }
    fun loadUser(){
        if(dataFile.exists()){
            val json = dataFile.readText()
            val type = object : TypeToken<Map<String, UsersDataClass>>() {}.type
            users = Gson().fromJson(json,type)
            generateJson = json
        }
    }
    fun deleteUser(website: String){
        users= users - website
        generateJson = GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(users)
        foundUser = null
        message = "Data deleted"
        saveUser()
    }
    fun randomPassword(){
        val letters = ('a'..'z') + ('A'..'Z')

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
    fun clearTextFields(){
        website = ""
        email=""
        password = ""
    }
}