package com.temankerja.temankerja.ui.user.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Information
import com.temankerja.temankerja.models.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {
    val data = MutableLiveData<DataOrException<List<Notification>, Exception>>()

    init {
        getNotification()
    }

    private fun getNotification() {
        viewModelScope.launch {
            data.postValue(repository.getNotification())
        }
    }
}