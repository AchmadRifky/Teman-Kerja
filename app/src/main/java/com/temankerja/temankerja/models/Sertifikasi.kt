package com.temankerja.temankerja.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sertifikasi(
    val title: String = "",
    val desc: String = "",
    val photo: Int = 0
) : Parcelable