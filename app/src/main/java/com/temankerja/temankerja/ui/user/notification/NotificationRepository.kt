package com.temankerja.temankerja.ui.user.notification

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.models.Applicants
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.models.Notification
import com.temankerja.temankerja.utils.Constants
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {
    suspend fun getNotification(): DataOrException<List<Notification>, Exception> {
        val status = DataOrException<List<Notification>, Exception>()
        val notification = mutableListOf<Notification>()
        try {
            val acc = db.collection(Constants.COLLECTION_APPLICANTS)
                .whereEqualTo("status", 1)
                .whereEqualTo("applicant_id", userPreference.getUser().id.toString())
                .get().await().map { document ->
                    document.toObject(Applicants::class.java)
                }
            acc.map {
                var tempJob = Jobs()
                db.collection(Constants.COLLECTION_JOBS)
                    .document(it.jobId!!)
                    .get()
                    .addOnSuccessListener { doc ->
                        tempJob = doc.toObject(Jobs::class.java)!!
                    }.await()
                notification.add(
                    Notification(
                        "diterima",
                        tempJob.jobsEmployeer.toString(),
                        tempJob.jobsTitle.toString(),
                    )
                )
            }
            status.data = notification
        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        return status
    }
}