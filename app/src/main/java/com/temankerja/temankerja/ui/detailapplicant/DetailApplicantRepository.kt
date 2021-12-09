package com.temankerja.temankerja.ui.detailapplicant
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
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
class DetailApplicantRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {
    suspend fun doRecruit(id: String): DataOrException<String, Exception> {
        val status = DataOrException<String, Exception>()
        try {
            db.collection(Constants.COLLECTION_APPLICANTS).document(id).update(
                "status", 1,
            ).addOnSuccessListener {
                status.data = id
            }.addOnFailureListener {
                status.e = it
            }.await()
        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        return status
    }

    suspend fun getDetailApplication(): DataOrException<Users, Exception> {
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