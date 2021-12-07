package com.temankerja.temankerja.ui.user.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.utils.Constants
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    suspend fun getLowonganFromUser(str: String): LiveData<DataOrException<List<Jobs>, Exception>> {
        val data = MutableLiveData<DataOrException<List<Jobs>, Exception>>()
        val status = DataOrException<List<Jobs>, Exception>()
        try {
            db.collection(Constants.COLLECTION_JOBS)
                .orderBy("jobs_title")
                .whereGreaterThanOrEqualTo("jobs_title", str)
                .whereLessThanOrEqualTo("jobs_title", str+'\uf8ff')
                .get()
                .addOnSuccessListener { document ->
                    status.data = document.toObjects(Jobs::class.java)
                }.await()
        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        data.postValue(status)
        return data
    }
}