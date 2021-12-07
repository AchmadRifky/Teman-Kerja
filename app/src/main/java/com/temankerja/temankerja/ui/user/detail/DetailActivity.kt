package com.temankerja.temankerja.ui.user.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivityDetailBinding
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.ui.user.resume.ResumeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_JOB = "extra_job"
    }
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val job = intent.getParcelableExtra<Jobs>(EXTRA_JOB) as Jobs
        setSupportActionBar(findViewById(R.id.toolbar2))
        binding.apply {
            userInclude.tvNavTitle.text = "Detail"
            tvCompanyName.text = job.jobsEmployeer
            tvJobTitle.text = job.jobsTitle
            tvJobLoc.text = job.jobsLoc
            tvJobDesc.text = job.jobsDesc
            tvWorkSchedule.text = job.jobsSchedule
            tvCompanyType.text = job.jobsCompanyCategory
            tvSalary.text = job.jobsSalary
            if(job.photo!=""){
                Glide.with(this@DetailActivity)
                    .load(job.photo)
                    .into(detailImgCompany)
            }

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.btnApply.setOnClickListener{
            Intent(this, ResumeActivity::class.java).apply {
                    putExtra(ResumeActivity.EXTRA_JOB, job)
                    startActivity(this)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}