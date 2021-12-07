package com.temankerja.temankerja.ui.user.resume
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.models.Applicants
import com.temankerja.temankerja.utils.Constants
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResumeRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {
    suspend fun storeApplicants(applicant: Applicants): DataOrException<Applicants, Exception> {
        val status = DataOrException<Applicants, Exception>()
        val data = hashMapOf(
            "applicant_id" to userPreference.getUser().id,
            "job_id" to applicant.jobId,
            "hr_id" to applicant.hrId,
            "status" to applicant.status,
            "resume" to applicant.resume
        )
        try {
            db.collection(Constants.COLLECTION_APPLICANTS)
                .add(data).addOnSuccessListener {
                    status.data = applicant
                }
                .addOnFailureListener{
                    status.e = it
                }.await()
        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        return status
    }
}