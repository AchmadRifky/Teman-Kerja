package com.temankerja.temankerja.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.temankerja.temankerja.HelperView
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        HelperView.setActionBar(
            supportActionBar,
            this,
            R.layout.actionbar_title_layout,
            getString(R.string.about)
        )

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}