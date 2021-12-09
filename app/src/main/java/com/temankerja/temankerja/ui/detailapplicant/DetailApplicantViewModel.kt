package com.temankerja.temankerja.ui.detailapplicant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailApplicantViewModel @Inject constructor(
    private val repository: DetailApplicantRepository
) : ViewModel() {
    val data = MutableLiveData<DataOrException<String, Exception>>()
    val dataUsers = MutableLiveData<DataOrException<Users, Exception>>()

    fun getDetailApplication() {
        viewModelScope.launch {
            dataUsers.postValue(repository.getDetailApplication())
        }
    }

    fun doRecruit(id: String) {
        viewModelScope.launch {
            data.postValue(repository.doRecruit(id))
        }
    }
}
