package com.temankerja.temankerja.ui.change

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivityChangeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityChangeBinding
    private val viewModel: ChangeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.userInclude.tvNavTitle.text = "Setting"
        binding.btnChange.setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_change -> {
                var isValid = true
                if (binding.tvEmailData.text.toString() != "") {
                    if (!isValidEmail(binding.tvEmailData.text.toString())) {
                        isValid = false
                        Toast.makeText(this, "Email not valid", Toast.LENGTH_SHORT).show()
                    }
                }
                if (isValid) {
                    viewModel.updateBiodata(
                        binding.tvEmailData.text.toString(),
                        binding.tvPasswordData.text.toString()
                    )
                    viewModel.dataUpdate.observe(this, {
                        if (it.data != null) {
                            Toast.makeText(this, "Successfuly updated", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}