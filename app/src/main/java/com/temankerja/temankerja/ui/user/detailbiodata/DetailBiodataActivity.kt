package com.temankerja.temankerja.ui.user.detailbiodata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivityDetailBiodataBinding
import com.temankerja.temankerja.ui.user.biodata.BiodataActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBiodataActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailBiodataBinding
    val viewModel : DetailBiodataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.userInclude.tvNavTitle.text = "Profile"
        binding.btnUpdate.setOnClickListener(this)
        viewModel.let {
            it.getDetailBiodata()
            it.data.observe(this, {
                if(it.e==null) {
                    binding.apply {
                        tvFullName.text = it.data?.fullname
                        tvLoc.text = it.data?.address
                        tvGender.text = it.data?.gender
                        tvNoKtp.text = it?.data?.noKtp
                        tvNoTelp.text = it?.data?.phone
                        tvKeterampilan.text = it?.data?.skills
                        if(it.data?.photo != null) {
                            Glide.with(this@DetailBiodataActivity)
                                .load(it.data?.photo)
                                .into(binding.imgProfile)
                        }
                    }
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_update-> {
                startActivity(Intent(this, BiodataActivity::class.java))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}