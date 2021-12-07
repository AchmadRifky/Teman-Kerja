package com.temankerja.temankerja.models

data class JobsApplicants(
    val id: String? = "",
    val applicantId: String,
    val resume: String,
    var user: Users,
)