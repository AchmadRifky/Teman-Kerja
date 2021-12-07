package com.temankerja.temankerja.ui.user.detailbiodata

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
class DetailBiodataViewModel @Inject constructor(
    private val repository: DetailBiodataRepository
) : ViewModel() {
    val data = MutableLiveData<DataOrException<Users, Exception>>()

    fun getDetailBiodata() {
        viewModelScope.launch {
            data.postValue(repository.getDetailBiodata())
        }
    }
}