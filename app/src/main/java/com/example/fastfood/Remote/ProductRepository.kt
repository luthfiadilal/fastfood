package com.example.fastfood.Remote

import android.content.Context
import com.example.fastfood.Database.FastFoodDatabase
import com.example.fastfood.Database.Product
import com.example.fastfood.Database.ProductDao
import com.example.fastfood.Database.ProductPriceQuantity

class ProductRepository(private val productDao: ProductDao) {

    companion object {
        @Volatile
        private var INSTANCE: ProductRepository? = null

        fun getInstance(context: Context): ProductRepository {
            return INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    val database = FastFoodDatabase.getDatabase(context)
                    INSTANCE = ProductRepository(database.productDao())
                }
                return INSTANCE as ProductRepository
            }
        }
    }

    // Menambahkan produk ke database
    suspend fun insertProduct(product: Product): Long {
        return productDao.insertProduct(product)
    }

    // Mendapatkan semua produk dari database
    suspend fun getAllProducts(): List<Product> {
        return productDao.getAllProducts()
    }

    suspend fun getProductById(productId: Int): Product? {
        return productDao.getProductById(productId)
    }

    suspend fun getAllPricesAndQuantities(): List<ProductPriceQuantity> {
        return productDao.getAllPricesAndQuantities()
    }

    // Memperbarui produk di database
    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }

    // Memperbarui kuantitas produk berdasarkan ID
    suspend fun updateProductQuantity(productId: Int, newQuantity: Int) {
        productDao.updateProductQuantity(productId, newQuantity)
    }
}