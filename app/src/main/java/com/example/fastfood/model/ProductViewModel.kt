package com.example.fastfood.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfood.Database.Product
import com.example.fastfood.Remote.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _allProducts = MutableLiveData<List<Product>>()
    val allProducts: LiveData<List<Product>> get() = _allProducts

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            productRepository.insertProduct(product)
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            val products = productRepository.getAllProducts()
            _allProducts.postValue(products)
            refreshProducts()
        }// Perbarui data yang dapat diamati
    }

    fun deleteAllProducts() {
        viewModelScope.launch {
            productRepository.deleteAllProducts()
        }
    }

    fun updateProductQuantity(productId: Int, newQuantity: Int, newPrice: Double) {
        viewModelScope.launch {
            // Perbarui kuantitas produk
            productRepository.updateProductQuantity(productId, newQuantity)

            // Ambil produk berdasarkan ID untuk memperbarui harga
            val product = productRepository.getProductById(productId)

            // Perbarui harga produk dan simpan kembali ke database
            product?.let {
                it.price = newPrice
                productRepository.updateProduct(it)
            }
            calculateTotalPrice()
            refreshProducts()
        }
    }

    // Function to calculate total price of all products
    private fun calculateTotalPrice() {
        viewModelScope.launch {
            val products = productRepository.getAllProducts()
            var total = 0.0
            for (product in products) {
                total += product.price * product.quantity
            }
            _totalPrice.postValue(total)
        }
    }

//    fun calculateTotalPrice() {
//        viewModelScope.launch {
//            val products = productRepository.getAllPricesAndQuantities()
//            var total = 0.0
//            for (product in products) {
//                total += product.price * product.quantity
//            }
//            _totalPrice.postValue(total)
//            refreshProducts()
//        }
//        refreshProducts()
//    }


    fun refreshProducts() {
        viewModelScope.launch {
            val products = productRepository.getAllProducts()
            _allProducts.postValue(products)
        }
    }

}