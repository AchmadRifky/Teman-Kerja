package com.temankerja.temankerja.ui.user.informasi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Information
import com.temankerja.temankerja.models.Jobs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class InformasiViewModel @Inject constructor(
    private val repository: InformasiRepository
) : ViewModel() {
    val data = MutableLiveData<DataOrException<List<Information>, Exception>>()

    init {
        getInformation()
    }

    private fun getInformation() {
        viewModelScope.launch {
            data.postValue(repository.getInformation())
        }
    }
}