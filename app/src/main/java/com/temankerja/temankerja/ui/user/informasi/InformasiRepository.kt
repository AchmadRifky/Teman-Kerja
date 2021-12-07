package com.temankerja.temankerja.ui.user.informasi
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.temankerja.temankerja.data.DataOrException
import com.temankerja.temankerja.models.Information
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.utils.Constants
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InformasiRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    suspend fun getInformation(): DataOrException<List<Information>, Exception> {
        val status = DataOrException<List<Information>, Exception>()
        try {
            status.data = db.collection(Constants.COLLECTION_INFO).get().await().map { document ->
                document.toObject(Information::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            status.e = e
        }
        return status
    }
}