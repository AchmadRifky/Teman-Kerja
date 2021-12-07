package com.temankerja.temankerja.ui.change
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.utils.Constants
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChangeRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {
        suspend fun updateUser(email: String, password: String): DataOrException<Boolean, Exception> {
        val status = DataOrException<Boolean, Exception>()
        try {
            if (email != "") {
                db.collection(Constants.COLLECTION_USERS).document(userPreference.getUser().id!!).update(
                    "email", email,
                ).addOnSuccessListener {
                    status.data = true
                }.addOnFailureListener {
                    status.e = it
                }.await()
            }

            if(password!=""){
                db.collection(Constants.COLLECTION_USERS).document(userPreference.getUser().id!!).update(
                    "password", password,
                ).addOnSuccessListener {
                    status.data = true
                }.addOnFailureListener {
                    status.e = it
                }.await()
            }

        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        return status
    }
}