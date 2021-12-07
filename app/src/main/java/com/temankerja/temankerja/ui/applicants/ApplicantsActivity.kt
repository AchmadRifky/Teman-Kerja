package com.temankerja.temankerja.ui.applicants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivityApplicantBinding
import com.temankerja.temankerja.databinding.ActivityDetailApplicantBinding
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.models.JobsApplicants
import com.temankerja.temankerja.ui.detailapplicant.DetailApplicantActivity
import com.temankerja.temankerja.ui.user.detail.DetailActivity
import com.temankerja.temankerja.ui.user.home.HomeAdapter
import com.temankerja.temankerja.ui.user.informasi.InformasiAdapter
import com.temankerja.temankerja.utils.StatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicantsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityApplicantBinding
    private val viewModel : ApplicantsViewModel by viewModels()
    private val adapter by lazy { ApplicantsAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBar.changeColor(window, this)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setupRv()
        viewModel.data.observe(this, {
            if(it.data != null) {
                adapter.setApplicantData(it.data!!)
            }
        })
        adapter.setOnItemClickCallback(object: ApplicantsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: JobsApplicants) {
                selectedData(data)
            }
        })
    }

    private fun selectedData(data: JobsApplicants) {
        Intent(this, DetailApplicantActivity::class.java).apply {
            putExtra(DetailApplicantActivity.EXTRA_APPLICANT_ID, data.id)
            putExtra(DetailApplicantActivity.EXTRA_RESUME, data.resume)
            putExtra(DetailApplicantActivity.EXTRA_GENDER, data.user.gender)
            putExtra(DetailApplicantActivity.EXTRA_FULLNAME, data.user.fullname)
            putExtra(DetailApplicantActivity.EXTRA_PHOTO, data.user.photo)
            startActivity(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupRv() {
        binding.rvApplicant.adapter = adapter
        binding.rvApplicant.layoutManager = LinearLayoutManager(this)
        binding.rvApplicant.setHasFixedSize(true)
    }
}