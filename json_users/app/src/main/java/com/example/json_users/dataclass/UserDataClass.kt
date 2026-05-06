package com.example.json_users.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class UserDataClass(
    val name: String,
    val age: Int
)
