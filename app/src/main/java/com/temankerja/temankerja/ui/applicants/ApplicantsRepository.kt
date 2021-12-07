package com.temankerja.temankerja.ui.applicants

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.models.Applicants
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.models.JobsApplicants
import com.temankerja.temankerja.models.Users
import com.temankerja.temankerja.utils.Constants
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicantsRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {
    suspend fun getApplicants(): DataOrException<List<JobsApplicants>, Exception> {
        val status = DataOrException<List<JobsApplicants>, Exception>()
        val applicantId = mutableListOf<String>()
        val jobsApplicants = mutableListOf<JobsApplicants>()
        try {
            db.collection(Constants.COLLECTION_APPLICANTS).whereEqualTo("status", 0).whereEqualTo("hr_id", userPreference.getUser().id).get().await().map { document ->
                document.toObject(Applicants::class.java).also {
                    applicantId.add(it.applicantId.toString())
                    jobsApplicants.add(
                        JobsApplicants(
                            document.id,
                            it.applicantId.toString(),
                            it.resume.toString(),
                            Users(),
                        )
                    )
                }
            }
            applicantId.mapIndexed { index, document ->
                db.collection(Constants.COLLECTION_USERS).document(document).get()
                    .addOnSuccessListener {
                        it.toObject(Users::class.java).also { user ->
                            jobsApplicants[index].user = user!!
                        }
                    }.addOnFailureListener {

                }.await()
            }

            status.data = jobsApplicants.toList()
        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        return status
    }
}