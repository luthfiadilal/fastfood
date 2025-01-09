package com.example.fastfood.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val image: String,

    val quantity: Int,

    var price: Double
)

