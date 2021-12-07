package com.temankerja.temankerja.models

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName

@Keep
data class Users(
    var id: String? = "",
    var address: String? = "",
    var email: String? = "",
    var fullname: String? = "",
    var gender: String? = "",
    @PropertyName("no_ktp")
    @set:PropertyName("no_ktp")
    @get:PropertyName("no_ktp")
    var noKtp: String? = "",
    var password: String? = "",
    var phone: String? = "",
    var skills: String? = "",
    var username: String? = "",
    var photo: String? = ""
)