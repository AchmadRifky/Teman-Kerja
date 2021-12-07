package com.temankerja.temankerja.utils

import android.content.Context
import android.view.Window
import androidx.core.content.ContextCompat
import com.temankerja.temankerja.R

object StatusBar {
    fun changeColor(window: Window, context: Context) {
        window.statusBarColor = ContextCompat.getColor(context, R.color.purple)
    }
}