package com.temankerja.temankerja.ui.user.resume

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivityResumeBinding
import com.temankerja.temankerja.models.Applicants
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.ui.success.SuccessActivity
import com.temankerja.temankerja.ui.user.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_JOB = "extra_job"
    }

    private lateinit var binding: ActivityResumeBinding

    private val viewModel: ResumeViewModel by viewModels()
    private lateinit var job: Jobs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResumeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        job = intent.getParcelableExtra<Jobs>(DetailActivity.EXTRA_JOB) as Jobs
        binding.btnSend.setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View?) {
        val resume = binding.inputResume.text.toString()
        if (TextUtils.isEmpty(resume)) {
            Toast.makeText(this, "Resume tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else {
            when (v?.id) {
                R.id.btn_send -> {
                    val applicant = Applicants(
                        "",
                        job.hrUsername,
                        job.id!!,
                        0,
                        binding.inputResume.text.toString()
                    )
                    viewModel.storeApplicant(applicant)
                    viewModel.data.observe(this, {
                        if (it.data != null) {
                            startActivity(Intent(this, SuccessActivity::class.java))
                            intent.putExtra(SuccessActivity.EXTRA_IS_RECRUITER, false)
                            finish()
                        }
                    })
                }
            }
        }
    }

}