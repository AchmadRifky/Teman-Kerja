package com.temankerja.temankerja.ui.applicants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Applicants
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.models.JobsApplicants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ApplicantsViewModel @Inject constructor(
    private val repository: ApplicantsRepository
) : ViewModel() {
    val data = MutableLiveData<DataOrException<List<JobsApplicants>, Exception>>()

    init {
        getApplicant()
    }

    private fun getApplicant() {
        viewModelScope.launch {
            data.postValue(repository.getApplicants())
        }
    }
}