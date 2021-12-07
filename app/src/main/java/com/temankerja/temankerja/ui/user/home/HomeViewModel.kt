package com.temankerja.temankerja.ui.user.home

import androidx.lifecycle.*
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Jobs
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
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