package com.temankerja.temankerja.data

data class SessionLogin(
    val id: String?="",
    val username: String?="",
    val email: String?="",
    val fullname: String?="",
    val photo: String?="",
    val isLogin: Boolean?=false
)