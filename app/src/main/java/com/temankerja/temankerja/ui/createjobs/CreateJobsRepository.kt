package com.temankerja.temankerja.ui.user.resume
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.models.Applicants
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.utils.Constants
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateJobsRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {
    suspend fun storeJobs (job: Jobs): DataOrException<Jobs, Exception> {
        val status = DataOrException<Jobs, Exception>()
        val data = hashMapOf(
            "hr_username" to userPreference.getUser().id,
            "jobs_company_category" to job.jobsCompanyCategory,
            "jobs_desc" to job.jobsDesc,
            "jobs_employeer" to job.jobsEmployeer,
            "jobs_loc" to job.jobsLoc,
            "jobs_salary" to job.jobsSalary,
            "jobs_schedule" to job.jobsSchedule,
            "jobs_title" to job.jobsTitle,
            "photo" to job.photo
        )
        try {
            db.collection(Constants.COLLECTION_JOBS)
                .add(data).addOnSuccessListener {
                    status.data = job
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