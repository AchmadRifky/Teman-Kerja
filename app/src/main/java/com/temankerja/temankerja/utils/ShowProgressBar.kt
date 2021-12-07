package com.temankerja.temankerja.utils

import android.view.View
import android.widget.ProgressBar

object ShowProgressBar {
    fun unvisible(v: ProgressBar){
            v.visibility = View.GONE
    }

    fun visible(v: ProgressBar){
        v.visibility = View.VISIBLE
    }
}