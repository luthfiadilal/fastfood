package com.example.fastfood.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fastfood.Adapter.KeranjangAdapter
import com.example.fastfood.R
import com.example.fastfood.databinding.ActivityKeranjangBinding
import com.example.fastfood.model.ProductViewModel
import com.example.fastfood.model.ProductViewModelFactory


class KeranjangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKeranjangBinding
    private lateinit var viewModel: ProductViewModel

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityKeranjangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this, ProductViewModelFactory.getInstance(applicationContext)).get(ProductViewModel::class.java)

        val recyclerView = binding.rvKeranjang
        val adapter = KeranjangAdapter(viewModel)

        recyclerView.adapter = adapter

        viewModel.allProducts.observe(this) { products ->
            adapter.submitList(products)
        }

        // Panggil getAllProducts untuk memuat data awal
        viewModel.getAllProducts()

        binding.buttonDelete.setOnClickListener {
            viewModel.deleteAllProducts()
        }

        // Mengamati perubahan total harga
        viewModel.totalPrice.observe(this, Observer { total ->
            val formattedPrice = String.format("%.0f", total)
            binding.total.text = "$formattedPrice" // Update UI dengan total harga viewModel.refreshProducts()

        })

        binding.buttonBuy.setOnClickListener {
            // Menghitung total harga produk di keranjang
            val totalPrice = viewModel.allProducts.value?.sumByDouble { it.price * it.quantity } ?: 0.0
            val formattedTotalPrice = String.format("%.0f", totalPrice)

            // Membuat AlertDialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pembayaran Berhasil")
                .setMessage("Pembayaran berhasil dengan total harga: Rp${formattedTotalPrice}")
                .setPositiveButton("OK") { _, _ ->
                    viewModel.deleteAllProducts()
                    viewModel.refreshProducts()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .setCancelable(false) // Agar dialog tidak bisa dibatalkan dengan menekan di luar
                .show()
        }





    }
}