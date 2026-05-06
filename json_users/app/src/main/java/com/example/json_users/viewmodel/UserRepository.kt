package com.example.json_users.viewmodel

import com.example.json_users.dataclass.UserDataClass
import com.google.gson.Gson

class UserRepository {
    fun getUser(): UserDataClass{
        val json = """{"name":"Daniel", "age":25}"""
        return Gson().fromJson(json, UserDataClass::class.java)
    }
}