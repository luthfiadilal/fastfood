package com.example.fastfood.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Product::class], version = 1, exportSchema = false)
abstract class FastFoodDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: FastFoodDatabase? = null

        fun getDatabase(context: Context): FastFoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FastFoodDatabase::class.java,
                    "fastfood_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}