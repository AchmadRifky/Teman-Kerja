package com.temankerja.temankerja.ui.recruiter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.ui.user.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RecruiterViewModel @Inject constructor(
    private val repository: RecruiterRepository
) : ViewModel() {
    private val _searchStringLiveData = MutableLiveData<String>()

    val jobsList = Transformations.switchMap(_searchStringLiveData) { string ->
        runBlocking {
            repository.getLowonganFromUser(string)
        }
    }

    init {
        _searchStringLiveData.value = ""
    }

    fun searchNameChanged(name: String) {
        _searchStringLiveData.value = name
    }
}