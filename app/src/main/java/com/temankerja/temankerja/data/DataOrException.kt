package com.temankerja.temankerja.data

import java.lang.Exception

data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var e: E? = null
)