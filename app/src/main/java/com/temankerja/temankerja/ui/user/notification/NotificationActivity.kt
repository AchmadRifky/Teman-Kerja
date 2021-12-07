package com.temankerja.temankerja.ui.user.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivityNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private val viewModel : NotificationViewModel by viewModels()
    private val adapter by lazy { NotificationAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.userInclude.tvNavTitle.text = "Notifikasi"
        setupRv()
        viewModel.data.observe(this, {
            if (it.data != null) {
                adapter.setNotificationData(it.data!!)
            }
        })
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