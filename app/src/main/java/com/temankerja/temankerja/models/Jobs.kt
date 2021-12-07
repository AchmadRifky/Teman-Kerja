package com.temankerja.temankerja.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Jobs(
    var id: String? = "",
    @PropertyName("hr_username")
    @get:PropertyName("hr_username")
    @set:PropertyName("hr_username")
    var hrUsername: String? = "",
    @PropertyName("jobs_company_category")
    @get:PropertyName("jobs_company_category")
    @set:PropertyName("jobs_company_category")
    var jobsCompanyCategory: String? = "",
    @PropertyName("jobs_desc")
    @get:PropertyName("jobs_desc")
    @set:PropertyName("jobs_desc")
    var jobsDesc: String? = "",
    @PropertyName("jobs_employeer")
    @get:PropertyName("jobs_employeer")
    @set:PropertyName("jobs_employeer")
    var jobsEmployeer: String? = "",
    @PropertyName("jobs_loc")
    @get:PropertyName("jobs_loc")
    @set:PropertyName("jobs_loc")
    var jobsLoc: String? = "",
    @PropertyName("jobs_salary")
    @get:PropertyName("jobs_salary")
    @set:PropertyName("jobs_salary")
    var jobsSalary: String? = "",
    @PropertyName("jobs_schedule")
    @get:PropertyName("jobs_schedule")
    @set:PropertyName("jobs_schedule")
    var jobsSchedule: String? = "",
    @PropertyName("jobs_title")
    @get:PropertyName("jobs_title")
    @set:PropertyName("jobs_title")
    var jobsTitle: String? = "",
    var photo: String? = "",
) : Parcelable