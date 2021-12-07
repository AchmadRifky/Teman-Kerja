package com.temankerja.temankerja.ui.success

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivitySuccessBinding
import com.temankerja.temankerja.ui.recruiter.RecruiterActivity
import com.temankerja.temankerja.ui.user.UserActivity

class SuccessActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySuccessBinding
    companion object{
        const val EXTRA_IS_RECRUITER = "extra_is_recruiter"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isRecruiter = intent.getBooleanExtra(EXTRA_IS_RECRUITER, false)
        if (isRecruiter) {
            binding.btnBack.setOnClickListener {
                startActivity(Intent(this, RecruiterActivity::class.java))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            binding.btnBack.setBackgroundColor(resources.getColor(R.color.purple))
            binding.tvLabelSuccess.text = "Calon telah direkrut"
        }else{
            binding.btnBack.setOnClickListener {
                binding.btnBack.setOnClickListener {
                    startActivity(Intent(this, UserActivity::class.java))
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }
    }
}