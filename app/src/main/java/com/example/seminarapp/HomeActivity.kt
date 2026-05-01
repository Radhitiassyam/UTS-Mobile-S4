package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.seminarapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var username: String = "Mahasiswa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        // Get username from intent
        username = intent.getStringExtra("USERNAME") ?: "Mahasiswa"

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        val sharedPreferences = getSharedPreferences("DataUser", MODE_PRIVATE)
        val savedName = sharedPreferences.getString("USER_NAME", "Mahasiswa") ?: "Mahasiswa"
        val displayName = savedName.replaceFirstChar { it.uppercase() }
        binding.tvWelcome.text = "Halo, $displayName!"
    }

    private fun setupClickListeners() {
        binding.btnDaftarSeminar.setOnClickListener {
            val intent = Intent(this, FormPendaftaranActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Keluar")
            .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
            .setPositiveButton("Ya, Keluar") { _, _ ->
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    // Prevent going back to login after successful login
    override fun onBackPressed() {
        // Do nothing or show exit confirmation
        showLogoutDialog()
    }
}
