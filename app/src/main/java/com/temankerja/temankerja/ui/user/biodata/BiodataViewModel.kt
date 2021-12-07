package com.temankerja.temankerja.ui.user.biodata

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
class BiodataViewModel @Inject constructor(
    private val repository: BiodataRepository
) : ViewModel() {
    val data = MutableLiveData<DataOrException<Users, Exception>>()
    val dataUpdate = MutableLiveData<DataOrException<Users, Exception>>()

    init {
        getBiodata()
    }

    fun getBiodata() {
        viewModelScope.launch {
            data.postValue(repository.getUser())
        }
    }

    fun updateBiodata(user: Users) {
        viewModelScope.launch {
            dataUpdate.postValue(repository.updateUser(user))
        }
    }
}