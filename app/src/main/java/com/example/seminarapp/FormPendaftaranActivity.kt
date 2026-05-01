package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.seminarapp.databinding.ActivityFormPendaftaranBinding

class FormPendaftaranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormPendaftaranBinding

    // Hardcoded seminar data
    private val seminarList = listOf(
        "-- Pilih Seminar --",
        "1. AI & Machine Learning untuk Pemula",
        "2. Kewirausahaan Digital di Era Startup",
        "3. Cybersecurity Awareness & Ethical Hacking",
        "4. UI/UX Design Thinking & Prototyping",
        "5. Cloud Computing & DevOps Fundamentals",
        "6. Data Science & Big Data Analytics",
        "7. Blockchain Technology & Web3 Development"
    )

    private var selectedSeminar: String = ""
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPendaftaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra("USERNAME") ?: ""

        supportActionBar?.hide()
        setupSpinner()
        setupRealTimeValidation()
        setupClickListeners()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            seminarList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSeminar.adapter = adapter

        binding.spinnerSeminar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedSeminar = if (position == 0) "" else seminarList[position]
                // Clear error when valid item selected
                if (position != 0) {
                    binding.tvSeminarError.visibility = View.GONE
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupRealTimeValidation() {
        // Nama real-time validation
        binding.etNama.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    binding.tilNama.error = null
                    binding.tilNama.isErrorEnabled = false
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Email real-time validation
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()
                when {
                    email.isEmpty() -> {
                        binding.tilEmail.error = null
                        binding.tilEmail.isErrorEnabled = false
                    }
                    !email.contains("@") -> {
                        binding.tilEmail.error = getString(R.string.error_email_format)
                        binding.tilEmail.isErrorEnabled = true
                    }
                    else -> {
                        binding.tilEmail.error = null
                        binding.tilEmail.isErrorEnabled = false
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Phone real-time validation
        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePhoneRealTime(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Checkbox listener
        binding.cbAgree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tvCheckboxError.visibility = View.GONE
            }
        }

        // Gender listener
        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                binding.tvGenderError.visibility = View.GONE
            }
        }
    }

    private fun validatePhoneRealTime(phone: String) {
        when {
            phone.isEmpty() -> {
                binding.tilPhone.error = null
                binding.tilPhone.isErrorEnabled = false
            }
            !phone.all { it.isDigit() } -> {
                binding.tilPhone.error = getString(R.string.error_phone_numeric)
                binding.tilPhone.isErrorEnabled = true
            }
            phone.length > 2 && !phone.startsWith("08") -> {
                binding.tilPhone.error = getString(R.string.error_phone_prefix)
                binding.tilPhone.isErrorEnabled = true
            }
            phone.length > 13 -> {
                binding.tilPhone.error = getString(R.string.error_phone_length)
                binding.tilPhone.isErrorEnabled = true
            }
            phone.length in 10..13 && phone.startsWith("08") -> {
                binding.tilPhone.error = null
                binding.tilPhone.isErrorEnabled = false
            }
            else -> {
                // Still typing, no error yet unless clearly wrong
                if (!phone.startsWith("0") && phone.isNotEmpty()) {
                    binding.tilPhone.error = getString(R.string.error_phone_prefix)
                    binding.tilPhone.isErrorEnabled = true
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            if (validateAllFields()) {
                showConfirmationDialog()
            }
        }
    }

    private fun validateAllFields(): Boolean {
        var isValid = true

        // Validate Nama
        val nama = binding.etNama.text.toString().trim()
        if (nama.isEmpty()) {
            binding.tilNama.error = getString(R.string.error_nama_empty)
            binding.tilNama.isErrorEnabled = true
            isValid = false
        } else {
            binding.tilNama.error = null
            binding.tilNama.isErrorEnabled = false
        }

        // Validate Email
        val email = binding.etEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_email_empty)
            binding.tilEmail.isErrorEnabled = true
            isValid = false
        } else if (!email.contains("@")) {
            binding.tilEmail.error = getString(R.string.error_email_format)
            binding.tilEmail.isErrorEnabled = true
            isValid = false
        } else {
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled = false
        }

        // Validate Phone
        val phone = binding.etPhone.text.toString().trim()
        if (phone.isEmpty()) {
            binding.tilPhone.error = getString(R.string.error_phone_empty)
            binding.tilPhone.isErrorEnabled = true
            isValid = false
        } else if (!phone.all { it.isDigit() }) {
            binding.tilPhone.error = getString(R.string.error_phone_numeric)
            binding.tilPhone.isErrorEnabled = true
            isValid = false
        } else if (!phone.startsWith("08")) {
            binding.tilPhone.error = getString(R.string.error_phone_prefix)
            binding.tilPhone.isErrorEnabled = true
            isValid = false
        } else if (phone.length !in 10..13) {
            binding.tilPhone.error = getString(R.string.error_phone_length)
            binding.tilPhone.isErrorEnabled = true
            isValid = false
        } else {
            binding.tilPhone.error = null
            binding.tilPhone.isErrorEnabled = false
        }

        // Validate Gender
        if (binding.rgGender.checkedRadioButtonId == -1) {
            binding.tvGenderError.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.tvGenderError.visibility = View.GONE
        }

        // Validate Seminar
        if (selectedSeminar.isEmpty()) {
            binding.tvSeminarError.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.tvSeminarError.visibility = View.GONE
        }

        // Validate Checkbox
        if (!binding.cbAgree.isChecked) {
            binding.tvCheckboxError.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.tvCheckboxError.visibility = View.GONE
        }

        if (!isValid) {
            Toast.makeText(this, "Mohon lengkapi semua field dengan benar", Toast.LENGTH_SHORT).show()
        }

        return isValid
    }

    private fun showConfirmationDialog() {
        val nama = binding.etNama.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val gender = if (binding.rbMale.isChecked) "Laki-laki" else "Perempuan"

        val message = """
            Apakah data yang Anda isi sudah benar?
            
            📋 Nama     : $nama
            📧 Email    : $email
            📱 HP       : $phone
            ⚧ Gender   : $gender
            🎯 Seminar  : ${selectedSeminar.substringAfter(". ")}
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Pendaftaran")
            .setMessage(message)
            .setPositiveButton("Ya, Kirim") { _, _ ->
                navigateToResult()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun navigateToResult() {
        val nama = binding.etNama.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val gender = if (binding.rbMale.isChecked) "Laki-laki" else "Perempuan"
        val seminarName = selectedSeminar.substringAfter(". ")

        val intent = Intent(this, HasilActivity::class.java).apply {
            putExtra("NAMA", nama)
            putExtra("EMAIL", email)
            putExtra("PHONE", phone)
            putExtra("GENDER", gender)
            putExtra("SEMINAR", seminarName)
            putExtra("USERNAME", username)
        }
        startActivity(intent)
    }
}
