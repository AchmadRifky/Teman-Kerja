package com.temankerja.temankerja.data

import android.content.Context
import com.temankerja.temankerja.models.Users
import com.temankerja.temankerja.utils.AuthType

class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val IS_LOGIN = "is_login"
        private const val USERNAME = "username"
        private const val FULLNAME = "fullname"
        private const val EMAIL = "email"
        private const val PHOTO = "photo"
        private const val ID = "ID"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(user: Users?, type: AuthType) {
        val editor = preferences.edit()
        when (type) {
            AuthType.LOGIN -> {
                editor.putString(ID, user?.id)
                editor.putString(USERNAME, user?.username)
                editor.putString(FULLNAME, user?.fullname)
                editor.putString(EMAIL, user?.email)
                editor.putString(PHOTO, user?.photo)
                editor.putBoolean(IS_LOGIN, true)
                editor.apply()
            }
            AuthType.LOGOUT -> {
                editor.putString(ID, null)
                editor.putString(USERNAME, null)
                editor.putString(FULLNAME, null)
                editor.putString(EMAIL, null)
                editor.putString(PHOTO, null)
                editor.putBoolean(IS_LOGIN, false)
                editor.apply()
            }
        }
    }

    fun getUser(): SessionLogin = SessionLogin(
        preferences.getString(ID, ""),
        preferences.getString(USERNAME, ""),
        preferences.getString(FULLNAME, ""),
        preferences.getString(EMAIL, ""),
        preferences.getString(PHOTO, ""),
        preferences.getBoolean(IS_LOGIN, false)
    )

}