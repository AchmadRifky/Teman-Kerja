package com.temankerja.temankerja.ui.change

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temankerja.temankerja.data.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ChangeViewModel @Inject constructor(
    private val repository: ChangeRepository
) : ViewModel() {
    val dataUpdate = MutableLiveData<DataOrException<Boolean, Exception>>()

    fun updateBiodata(email: String, password: String) {
        viewModelScope.launch {
            dataUpdate.postValue(repository.updateUser(email, password))
        }
    }
}