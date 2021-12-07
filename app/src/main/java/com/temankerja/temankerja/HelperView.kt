package com.temankerja.temankerja

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

object HelperView {
    fun setActionBar(
        supportActionBar: ActionBar?,
        activity: AppCompatActivity,
        titleLayout: Int,
        titleText: String
    ) {
        val abar: ActionBar? = supportActionBar

        val viewActionBar: View = activity.layoutInflater.inflate(titleLayout, null)
        val params: ActionBar.LayoutParams =
            ActionBar.LayoutParams( //Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
            )
        viewActionBar.findViewById<TextView>(R.id.actionbar_textview).text = titleText
        if (abar != null) {
            abar.setCustomView(viewActionBar, params)
            abar.setDisplayShowCustomEnabled(true)
            abar.setDisplayShowTitleEnabled(false)
            abar.setDisplayHomeAsUpEnabled(true)
            abar.setHomeButtonEnabled(true)
        }
    }
}