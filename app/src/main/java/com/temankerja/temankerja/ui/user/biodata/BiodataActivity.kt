package com.temankerja.temankerja.ui.user.biodata

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ActivityBiodataBinding
import com.temankerja.temankerja.models.Users
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BiodataActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityBiodataBinding
    private val viewModel: BiodataViewModel by viewModels()
    private lateinit var id: String
    private lateinit var storeRef: StorageReference
    val storage = Firebase.storage
    private lateinit var downloadUri: String
    var isCompletedUpload = false
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeRef = storage.reference
        binding = ActivityBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.userInclude.tvNavTitle.text = "Biodata"
        binding.btnSave.setOnClickListener(this)
        binding.tvUpload.setOnClickListener(this)
        viewModel.data.observe(this, {
            if (it.data != null) {
                id = it.data!!.id!!
                binding.apply {
                    tvFullnameData.setText(it.data?.fullname)
                    tvAddressData.setText(it.data?.address)
                    tvNoKtpData.setText(it.data?.noKtp)
                    tvNoTelpData.setText(it.data?.phone)
                    tvSkillsData.setText(it.data?.skills)
                    if(it.data?.photo != null) {
                        Glide.with(this@BiodataActivity)
                            .load(it.data?.photo)
                            .into(binding.imgProfile)
                    }
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_save -> {
                if(!isCompletedUpload){
                    val user = Users(
                        id,
                        binding.tvAddressData.text.toString(),
                        "",
                        binding.tvFullnameData.text.toString(),
                        "Laki-Laki",
                        binding.tvNoKtpData.text.toString(),
                        "",
                        binding.tvNoTelpData.text.toString(),
                        binding.tvSkillsData.text.toString(),
                        "",
                    )
                    viewModel.updateBiodata(user)
                    viewModel.dataUpdate.observe(this, {
                        if (it.data != null) {
                            Toast.makeText(
                                this,
                                "Data successfuly updated!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }else{
                    val ref = storeRef.child("images/${uri.lastPathSegment}")
                    val uploadTask = ref.putFile(uri)

                    uploadTask.addOnFailureListener {
                    }.addOnSuccessListener { taskSnapshot ->
                        uploadTask.continueWithTask { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    throw it
                                }
                            }
                            ref.downloadUrl
                        }.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val ref = storeRef.child("images/${uri.lastPathSegment}")
                                val uploadTask = ref.putFile(uri)

                                uploadTask.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Upload photo fail : ${it.localizedMessage}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.addOnSuccessListener { taskSnapshot ->
                                    uploadTask.continueWithTask { task ->
                                        if (!task.isSuccessful) {
                                            task.exception?.let {
                                                throw it
                                            }
                                        }
                                        ref.downloadUrl
                                    }.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            downloadUri = task.result.toString()
                                            val user = Users(
                                                id,
                                                binding.tvAddressData.text.toString(),
                                                "",
                                                binding.tvFullnameData.text.toString(),
                                                "Laki-Laki",
                                                binding.tvNoKtpData.text.toString(),
                                                "",
                                                binding.tvNoTelpData.text.toString(),
                                                binding.tvSkillsData.text.toString(),
                                                "",
                                                downloadUri
                                            )
                                            viewModel.updateBiodata(user)
                                            viewModel.dataUpdate.observe(this, {
                                                if (it.data != null) {
                                                    Toast.makeText(
                                                        this,
                                                        "Data successfuly updated!",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            })
                                        }
                                    }
                                }.addOnCompleteListener {
                                    Toast.makeText(this, "Update photo successfuly, please try to relogin for data changed", Toast.LENGTH_SHORT)
                                        .show()
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Upload photo fail: ${it.localizedMessage}", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }.addOnCompleteListener {
                        Toast.makeText(this, "Upload photo successfuly", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.tv_upload -> {
                ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(
                        1080,
                        1080
                    )
                    .start()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            uri = data?.data!!
            binding.imgProfile.setImageURI(uri)
            isCompletedUpload = true
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}