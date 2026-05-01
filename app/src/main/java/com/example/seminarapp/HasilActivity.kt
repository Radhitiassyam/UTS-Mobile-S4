package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seminarapp.databinding.ActivityHasilBinding

class HasilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        displayResult()
        setupClickListeners()
    }

    private fun displayResult() {
        val nama = intent.getStringExtra("NAMA") ?: "-"
        val email = intent.getStringExtra("EMAIL") ?: "-"
        val phone = intent.getStringExtra("PHONE") ?: "-"
        val gender = intent.getStringExtra("GENDER") ?: "-"
        val seminar = intent.getStringExtra("SEMINAR") ?: "-"

        binding.tvResultNama.text = nama
        binding.tvResultEmail.text = email
        binding.tvResultPhone.text = phone
        binding.tvResultGender.text = gender
        binding.tvResultSeminar.text = seminar
    }

    private fun setupClickListeners() {
        binding.btnBackHome.setOnClickListener {
            val username = intent.getStringExtra("USERNAME") ?: ""
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("USERNAME", username)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
    }

    // Prevent going back to form
    override fun onBackPressed() {
        binding.btnBackHome.performClick()
    }
}
