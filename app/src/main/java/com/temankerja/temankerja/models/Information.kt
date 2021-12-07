package com.temankerja.temankerja.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Information(
    var title: String? = "",
    var desc: String? = "",
    var img: String? = "",
) : Parcelable
