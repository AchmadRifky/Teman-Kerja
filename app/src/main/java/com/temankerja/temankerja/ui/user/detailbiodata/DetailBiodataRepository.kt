package com.temankerja.temankerja.ui.user.detailbiodata

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.toObject
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.models.Users
import com.temankerja.temankerja.utils.Constants
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailBiodataRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {
    suspend fun getDetailBiodata(): DataOrException<Users, Exception> {
        val status = DataOrException<Users, Exception>()
        try {
            status.data = db.collection("users")
                .document(userPreference.getUser().id.toString())
                .get().await().toObject(Users::class.java)
        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        return status
    }
}