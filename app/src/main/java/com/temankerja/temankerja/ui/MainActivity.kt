package com.temankerja.temankerja.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivityMainBinding
import com.temankerja.temankerja.ui.recruiter.RecruiterActivity
import com.temankerja.temankerja.ui.user.UserActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUser.setOnClickListener(this)
        binding.btnRecruiter.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_user -> {
                startActivity(Intent(this@MainActivity, UserActivity::class.java))
            }
            R.id.btn_recruiter -> {
                startActivity(Intent(this@MainActivity, RecruiterActivity::class.java))
            }
        }
    }
}