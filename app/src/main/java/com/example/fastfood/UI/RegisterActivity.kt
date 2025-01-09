package com.example.fastfood.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.fastfood.Database.User
import com.example.fastfood.R
import com.example.fastfood.databinding.ActivityRegisterBinding
import com.example.fastfood.model.UserViewModel
import com.example.fastfood.model.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
//    private lateinit var viewmodel: UserViewModel
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fastfood-e9b84-default-rtdb.firebaseio.com/")

//        viewmodel= ViewModelProvider(this, ViewModelFactory.getInstance(applicationContext)).get(UserViewModel::class.java)
//
//
//        viewmodel.isLoading.observe(this) { isLoading ->
//            if (isLoading) {
//                binding.loadingContainer.visibility = android.view.View.VISIBLE
//            } else {
//                binding.loadingContainer.visibility = android.view.View.GONE
//            }
//        }
        binding.buttonSignup.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val email = binding.edtEmail.text.toString()
            val phone = binding.edtPhone.text.toString()
            val password = binding.edtPassword.text.toString()

            // Periksa jika ada field yang kosong
            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Kosong, belum ada data diisi", Toast.LENGTH_SHORT).show()
            } else {
                // Jika semua field terisi, simpan ke Firebase


                database = FirebaseDatabase.getInstance().getReference("users")

                val sanitizedEmail = email.replace(".", "_")
                val userRef = database.child(sanitizedEmail) // username sebagai key
                userRef.child("username").setValue(username)
                userRef.child("email").setValue(email)
                userRef.child("phone").setValue(phone)
                userRef.child("password").setValue(password)

                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

//            viewmodel.registerUser(User(
//                username = username,
//                email = email,
//                phone = phone,
//                password = password
//            ))

//            viewmodel.registerResult.observe(this) { user ->
//                if (user != null) {
//                    val intent = android.content.Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    Snackbar.make(binding.root, "Registration failed", Snackbar.LENGTH_SHORT).show()
//                }
//            }
        }





}