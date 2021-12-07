package com.temankerja.temankerja.ui.user.biodata

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.models.Information
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.models.Users
import com.temankerja.temankerja.utils.Constants
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BiodataRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {
    suspend fun updateUser(user: Users): DataOrException<Users, Exception> {
        val status = DataOrException<Users, Exception>()
        try {
            if (user.photo != "") {
                db.collection(Constants.COLLECTION_USERS).document(userPreference.getUser().id!!)
                    .update(
                        "address", user.address,
                        "fullname", user.fullname,
                        "gender", user.gender,
                        "no_ktp", user.noKtp,
                        "phone", user.phone,
                        "skills", user.skills,
                        "photo", user.photo
                    ).addOnSuccessListener {
                    status.data = user
                }.addOnFailureListener {
                    status.e = it
                }.await()
            }else{
                db.collection(Constants.COLLECTION_USERS).document(userPreference.getUser().id!!)
                    .update(
                        "address", user.address,
                        "fullname", user.fullname,
                        "gender", user.gender,
                        "no_ktp", user.noKtp,
                        "phone", user.phone,
                        "skills", user.skills,
                    ).addOnSuccessListener {
                        status.data = user
                    }.addOnFailureListener {
                        status.e = it
                    }.await()
            }
        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        return status
    }

    suspend fun getUser(): DataOrException<Users, Exception> {
        val status = DataOrException<Users, Exception>()
        try {
            db.collection(Constants.COLLECTION_USERS).document(userPreference.getUser().id!!).get()
                .addOnSuccessListener {
                    status.data = it.toObject<Users>().also { u ->
                        if (u != null) {
                            u.id = it.id
                        }
                    }
                }.await()
        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        return status
    }
}