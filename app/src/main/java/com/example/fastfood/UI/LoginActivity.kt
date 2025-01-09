package com.example.fastfood.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.fastfood.R
import com.example.fastfood.databinding.ActivityLoginBinding
import com.example.fastfood.model.UserViewModel
import com.example.fastfood.model.ViewModelFactory
import com.example.fastfood.pref.UserPreference
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
//    private lateinit var viewmodel: UserViewModel
    private lateinit var userPreference: UserPreference
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fastfood-e9b84-default-rtdb.firebaseio.com/")

        userPreference = UserPreference(this)
//
//        viewmodel= ViewModelProvider(this, ViewModelFactory.getInstance(applicationContext)).get(UserViewModel::class.java)

        binding.buttonSignup.setOnClickListener {
            val intent = android.content.Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

//        viewmodel.isLoading.observe(this) { isLoading ->
//            if (isLoading) {
//                binding.loadingContainer.visibility = android.view.View.VISIBLE
//            } else {
//                binding.loadingContainer.visibility = android.view.View.GONE
//            }
//        }


        binding.buttonLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            // Ganti titik pada email dengan garis bawah
            val sanitizedEmail = email.replace(".", "_")

            database = FirebaseDatabase.getInstance().getReference("users")

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(sanitizedEmail).exists()) {
                            // Periksa password
                            val storedPassword = snapshot.child(sanitizedEmail).child("password").getValue(String::class.java)
                            if (storedPassword == password) {
                                val username = snapshot.child(sanitizedEmail).child("username").getValue(String::class.java)
                                if (username != null) {
                                    val userPreference = UserPreference(this@LoginActivity)
                                    userPreference.saveUser(username, email)  // Simpan user yang login

                                    // Pindah ke MainActivity
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                            } else {
                                Snackbar.make(binding.root, "Incorrect password", Snackbar.LENGTH_SHORT).show()
                            }
                        } else {
                            Snackbar.make(binding.root, "Email not found", Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Tangani error jika terjadi
                        Snackbar.make(binding.root, "Login failed", Snackbar.LENGTH_SHORT).show()
                    }
                })
            }
        }

    }
}