package com.temankerja.temankerja.ui.createjobs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.models.Users
import com.temankerja.temankerja.ui.user.resume.CreateJobsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateJobsViewModel @Inject constructor(
    private val repository: CreateJobsRepository
) : ViewModel() {
    val data = MutableLiveData<DataOrException<Jobs, Exception>>()

    fun storeJobs(job: Jobs) {
        viewModelScope.launch {
            data.postValue(repository.storeJobs(job))
        }
    }
}