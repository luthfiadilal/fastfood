package com.example.fastfood.UI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastfood.Adapter.FoodAdapter
import com.example.fastfood.Adapter.FoodListAdapter
import com.example.fastfood.R
import com.example.fastfood.databinding.ActivityMainBinding
import com.example.fastfood.food.FoodList
import com.example.fastfood.model.ProductViewModel
import com.example.fastfood.model.ProductViewModelFactory
import com.example.fastfood.model.ViewModelFactory
import com.example.fastfood.pref.UserPreference
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreference: UserPreference
    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this, ProductViewModelFactory.getInstance(applicationContext)).get(ProductViewModel::class.java)

        userPreference = UserPreference(this)

        binding.buttonKeranjang.setOnClickListener {
            startActivity(Intent(this, KeranjangActivity::class.java))
        }

        val username = userPreference.getUsername()
        binding.toolbar.title = username

        val recyclerView = binding.rvFoodFavorite
        val adapter = FoodAdapter(viewModel)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        adapter.submitList(FoodList.foodList)

        val foodRecyclerView = binding.rvFood
        val foodAdapter = FoodListAdapter(viewModel)

        foodRecyclerView.adapter = foodAdapter

        foodAdapter.submitList(FoodList.foodList)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)




    }

    // Menampilkan menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    // Menangani klik item menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                // Lakukan logout
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Fungsi logout
    private fun logout() {
        // Clear user data
        userPreference.clearUser()

        // Redirect ke SplashScreen atau LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}