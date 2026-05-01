package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seminarapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    // Hardcoded user credentials
    private val validUsers = mapOf(
        "admin" to "admin123",
        "mahasiswa" to "password",
        "utb" to "utb2025",
        "user" to "123456"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupRealTimeValidation()
        setupClickListeners()
    }

    private fun setupRealTimeValidation() {
        // Username real-time validation
        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    binding.tilUsername.error = null
                    binding.tilUsername.isErrorEnabled = false
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Password real-time validation
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    binding.tilPassword.error = null
                    binding.tilPassword.isErrorEnabled = false
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val inputUser = binding.etUsername.text.toString().trim()
            val inputPass = binding.etPassword.text.toString().trim()

            // 1. Ambil data dari SharedPreferences (Hasil Register)
            val sharedPreferences = getSharedPreferences("DataUser", MODE_PRIVATE)
            val savedEmail = sharedPreferences.getString("USER_EMAIL", "")
            val savedPassword = sharedPreferences.getString("USER_PASSWORD", "")

            // 2. Cek apakah input cocok dengan data register
            val isRegisteredUser = inputUser == savedEmail && inputPass == savedPassword

            // 3. Cek apakah input cocok dengan data hardcoded (admin, mahasiswa, dsb)
            val isHardcodedUser = validUsers[inputUser] == inputPass

            // 4. Gabungkan: Jika cocok di salah satu, maka Berhasil
            if (isRegisteredUser || isHardcodedUser) {
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Username/Email atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
