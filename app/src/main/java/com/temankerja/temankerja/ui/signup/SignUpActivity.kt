package com.temankerja.temankerja.ui.signup

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = getString(R.string.daftar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnRegister.setOnClickListener {
            if (validate()) {
                db.collection("users")
                    .whereEqualTo("username", binding.tvUsernameData.text.toString())
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            showToast("Username sudah ada, silakan buat dengan username lain")
                        } else {
                            doRegister()
                        }
                    }
                    .addOnFailureListener { e ->
                        showToast("Fail : " + e.localizedMessage)
                    }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun doRegister() {
        val user = hashMapOf(
            "username" to binding.tvUsernameData.text.toString(),
            "email" to binding.tvEmailData.text.toString(),
            "password" to binding.tvPasswordData.text.toString(),
            "fullname" to null,
            "gender" to null,
            "address" to null,
            "no_ktp" to null,
            "phone" to null,
            "skills" to null,
            "photo" to null
        )
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                clearAllText()
                Toast.makeText(this, "Register Berhasil", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Register Failed : " + e.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun validate(): Boolean {
        val passData = binding.tvPasswordData.text.toString()
        val repassData = binding.tvRepasswordData.text.toString()
        val usernameData = binding.tvUsernameData.text.toString()
        val emailData = binding.tvEmailData.text.toString()
        when {
            usernameData == "" -> {
                showToast("Username tidak boleh kosong")
                return false
            }
            emailData == "" -> {
                showToast("Email tidak boleh kosong")
                return false
            }
            !isValidEmail(emailData) -> {
                showToast("Email tidak sesuai format")
                return false
            }
            passData == "" -> {
                showToast("Kata sandi tidak boleh kosong")
                return false
            }
            passData != repassData -> {
                showToast("Kata sandi tidak sama")
                return false
            }
            else -> return true
        }
    }

    private fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    private fun clearAllText() {
        binding.apply {
            this.tvEmailData.text?.clear()
            this.tvUsernameData.text?.clear()
            this.tvPasswordData.text?.clear()
            this.tvRepasswordData.text?.clear()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}