package com.example.fastfood.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val username: String,

    val email: String,

    val phone: String,

    val password: String
)

