package com.temankerja.temankerja.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Applicants(
    @PropertyName("applicant_id")
    @set:PropertyName("applicant_id")
    @get:PropertyName("applicant_id")
    var applicantId : String? = "",
    @PropertyName("hr_id")
    @set:PropertyName("hr_id")
    @get:PropertyName("hr_id")
    var hrId : String?= "",
    @PropertyName("job_id")
    @set:PropertyName("job_id")
    @get:PropertyName("job_id")
    var jobId : String?="",
    var status : Int?=0,
    var resume: String?="",
    ) : Parcelable
