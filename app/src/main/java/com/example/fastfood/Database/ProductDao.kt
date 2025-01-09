package com.example.fastfood.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    suspend fun getProductById(productId: Int): Product?

    // Fungsi untuk mengambil harga dan kuantitas produk
    @Query("SELECT price, quantity FROM products")
    suspend fun getAllPricesAndQuantities(): List<ProductPriceQuantity>

    @Update
    suspend fun updateProduct(product: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()


    @Query("UPDATE products SET quantity = :newQuantity WHERE id = :productId")
    suspend fun updateProductQuantity(productId: Int, newQuantity: Int)
}