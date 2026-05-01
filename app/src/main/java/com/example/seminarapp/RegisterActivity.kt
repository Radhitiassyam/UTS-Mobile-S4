package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seminarapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupRealTimeValidation()
        setupClickListeners()
    }

    private fun setupRealTimeValidation() {
        // Nama real-time
        binding.etRegName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    binding.tilRegName.error = null
                    binding.tilRegName.isErrorEnabled = false
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Email real-time
        binding.etRegEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()
                when {
                    email.isEmpty() -> {
                        binding.tilRegEmail.error = null
                        binding.tilRegEmail.isErrorEnabled = false
                    }
                    !email.contains("@") -> {
                        binding.tilRegEmail.error = getString(R.string.error_email_format)
                    }
                    else -> {
                        binding.tilRegEmail.error = null
                        binding.tilRegEmail.isErrorEnabled = false
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Password real-time
        binding.etRegPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pass = s.toString()
                when {
                    pass.isEmpty() -> {
                        binding.tilRegPassword.error = null
                        binding.tilRegPassword.isErrorEnabled = false
                    }
                    pass.length < 6 -> {
                        binding.tilRegPassword.error = getString(R.string.error_password_min)
                    }
                    else -> {
                        binding.tilRegPassword.error = null
                        binding.tilRegPassword.isErrorEnabled = false
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Confirm password real-time
        binding.etRegConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val confirmPass = s.toString()
                val pass = binding.etRegPassword.text.toString()
                when {
                    confirmPass.isEmpty() -> {
                        binding.tilRegConfirmPassword.error = null
                        binding.tilRegConfirmPassword.isErrorEnabled = false
                    }
                    confirmPass != pass -> {
                        binding.tilRegConfirmPassword.error = getString(R.string.error_password_mismatch)
                    }
                    else -> {
                        binding.tilRegConfirmPassword.error = null
                        binding.tilRegConfirmPassword.isErrorEnabled = false
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            if (validateInput()) {
                // --- BAGIAN PENYIMPANAN DATA (SHARED PREFERENCES) ---
                val sharedPreferences = getSharedPreferences("DataUser", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                // Ambil teks dari inputan user
                val email = binding.etRegEmail.text.toString().trim()
                val password = binding.etRegPassword.text.toString().trim()
                val name = binding.etRegName.text.toString().trim()

                // Simpan ke memori HP
                editor.putString("USER_EMAIL", email)
                editor.putString("USER_PASSWORD", password)
                editor.putString("USER_NAME", name)
                editor.apply() // Eksekusi penyimpanan
                // ----------------------------------------------------

                Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_LONG).show()

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateInput(): Boolean {
        val name = binding.etRegName.text.toString().trim()
        val email = binding.etRegEmail.text.toString().trim()
        val password = binding.etRegPassword.text.toString().trim()
        val confirmPassword = binding.etRegConfirmPassword.text.toString().trim()
        var isValid = true

        if (name.isEmpty()) {
            binding.tilRegName.error = getString(R.string.error_name_empty)
            isValid = false
        }

        if (email.isEmpty()) {
            binding.tilRegEmail.error = "Email tidak boleh kosong"
            isValid = false
        } else if (!email.contains("@")) {
            binding.tilRegEmail.error = getString(R.string.error_email_format)
            isValid = false
        }

        if (password.isEmpty()) {
            binding.tilRegPassword.error = "Password tidak boleh kosong"
            isValid = false
        } else if (password.length < 6) {
            binding.tilRegPassword.error = getString(R.string.error_password_min)
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            binding.tilRegConfirmPassword.error = "Konfirmasi password tidak boleh kosong"
            isValid = false
        } else if (confirmPassword != password) {
            binding.tilRegConfirmPassword.error = getString(R.string.error_password_mismatch)
            isValid = false
        }

        return isValid
    }
}
