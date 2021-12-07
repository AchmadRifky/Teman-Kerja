package com.temankerja.temankerja.ui.createjobs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.temankerja.temankerja.databinding.ActivityCreateJobsBinding
import dagger.hilt.android.AndroidEntryPoint
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.temankerja.temankerja.R
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.ui.recruiter.RecruiterActivity
import com.temankerja.temankerja.utils.StatusBar


@AndroidEntryPoint
class CreateJobsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCreateJobsBinding
    private lateinit var spinner: String
    private lateinit var uri: Uri
    val storage = Firebase.storage
    private lateinit var storeRef: StorageReference
    private lateinit var downloadUri: String
    var isCompletedUpload = false
    private val viewModel: CreateJobsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.spinner_lokasi)
        )
        storeRef = storage.reference

        binding.userInclude.tvNavTitle.text = "Lowongan"
        binding.spinnerLokasi.adapter = adapter
        binding.spinnerLokasi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                long: Long
            ) {
                spinner = binding.spinnerLokasi.selectedItem.toString()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }
        StatusBar.changeColor(window, this)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.apply {
            btnPost.setOnClickListener(this@CreateJobsActivity)
            tvUpload.setOnClickListener(this@CreateJobsActivity)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_post -> {
                if (isCompletedUpload) {

                    val ref = storeRef.child("images_work/${uri.lastPathSegment}")
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
                                            binding.apply {
                                                val job = Jobs(
                                                    "",
                                                    "",
                                                    binding.tvCompanyCategoryData.text.toString(),
                                                    binding.tvDeskripsiData.text.toString(),
                                                    binding.tvCompanyNameData.text.toString(),
                                                    spinner,
                                                    binding.tvGajiData.text.toString(),
                                                    binding.tvJadwalKerjaData.text.toString(),
                                                    binding.tvPosisiData.text.toString(),
                                                    downloadUri
                                                )
                                                viewModel.storeJobs(job)
                                                viewModel.data.observe(this@CreateJobsActivity, {
                                                    if (it.data != null) {
                                                        Toast.makeText(
                                                            this@CreateJobsActivity,
                                                            "Berhasil menambahkan pekerjaan baru",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        startActivity(
                                                            Intent(
                                                                this@CreateJobsActivity,
                                                                RecruiterActivity::class.java
                                                            )
                                                        )
                                                        finish()
                                                    }
                                                })
                                            }
                                        }
                                    }
                                }.addOnCompleteListener {
                                    Toast.makeText(
                                        this,
                                        "Upload photo successfuly",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Upload photo fail: ${it.localizedMessage}",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                    }.addOnCompleteListener {
                        Toast.makeText(this, "Upload photo successfuly", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Photo is required", Toast.LENGTH_SHORT).show()
                }

            }
            R.id.tv_upload -> {
                ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(
                        1080,
                        1080
                    ).start()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
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