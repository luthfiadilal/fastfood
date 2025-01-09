package com.example.fastfood.Remote

import android.content.Context
import com.example.fastfood.Database.FastFoodDatabase
import com.example.fastfood.Database.User
import com.example.fastfood.Database.UserDao

class UserRepository(private val userDao: UserDao) {
    companion object {

        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    val database = FastFoodDatabase.getDatabase(context)
                    INSTANCE = UserRepository(database.userDao()) // Perbaiki dengan menyimpan instance
                }
                INSTANCE as UserRepository
            }
        }

    }


    suspend fun registerUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
}