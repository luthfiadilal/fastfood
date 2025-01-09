package com.example.fastfood.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastfood.Remote.ProductRepository
import com.example.fastfood.Remote.UserRepository

class ProductViewModelFactory(private val productRepository: ProductRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ProductViewModel::class.java) ->{
                ProductViewModel(productRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ProductViewModelFactory? = null

        fun getInstance(context: Context): ProductViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ProductViewModelFactory(ProductRepository.getInstance(context)).also { INSTANCE = it }
            }
        }

    }
}