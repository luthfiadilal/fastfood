package com.example.fastfood.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfood.Database.User
import com.example.fastfood.Remote.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel()  {

        private val _registerResult = MutableLiveData<Long>()
        val registerResult: LiveData<Long> get() = _registerResult

        private val _loginResult = MutableLiveData<User?>()
        val loginResult: LiveData<User?> get() = _loginResult

        private val _userDetails = MutableLiveData<User?>()
        val userDetails: LiveData<User?> get() = _userDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

        fun registerUser(user: User) {
            _isLoading.value = true
            viewModelScope.launch {
                val result = userRepository.registerUser(user)
                _registerResult.postValue(result)
                _isLoading.value = false
            }
        }

        fun loginUser(email: String, password: String) {
            _isLoading.value = true
            viewModelScope.launch {
                val user = userRepository.loginUser(email, password)
                _loginResult.postValue(user)
                _isLoading.value = false
            }
        }

        fun getUserByUsername(username: String) {
            viewModelScope.launch {
                val user = userRepository.getUserByUsername(username)
                _userDetails.postValue(user)
            }
        }
}