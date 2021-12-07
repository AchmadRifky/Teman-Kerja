package com.temankerja.temankerja.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.temankerja.temankerja.R
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.databinding.ActivityLoginBinding
import com.temankerja.temankerja.models.Users
import com.temankerja.temankerja.ui.MainActivity
import com.temankerja.temankerja.ui.signup.SignUpActivity
import com.temankerja.temankerja.utils.AuthType

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    val db = Firebase.firestore
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreference = UserPreference(this)

        binding.apply {
            tvDaftar.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }

            btnMasuk.setOnClickListener {
                signIn()
            }
        }
        if(userPreference.getUser().isLogin != false) {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    private fun signIn() {
        val username = binding.tvUsernameData.text.toString()
        val password = binding.tvPasswordData.text.toString()
        db.collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    val data = snapshot.toObjects(Users::class.java).also {
                        it[0].id = snapshot.documents.first().id
                    }
                    if(data.first().password == password){
                        userPreference.setUser(data.first(), AuthType.LOGIN)
                        Intent(this, MainActivity::class.java).apply {
                            startActivity(this)
                        }
                    }else{
                        showToast("Password tidak valid")
                    }
                }else {
                    showToast("Username tidak ditemukan")
                }
            }
            .addOnFailureListener { exception ->
                showToast("Fail : " + exception.localizedMessage)
            }
    }

    fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}