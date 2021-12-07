package com.temankerja.temankerja.ui.user.resume

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Applicants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val repository: ResumeRepository
) : ViewModel() {
    val data = MutableLiveData<DataOrException<Applicants, Exception>>()

    fun storeApplicant(applicant: Applicants) {
        viewModelScope.launch {
            data.postValue(repository.storeApplicants(applicant))
        }
    }
}