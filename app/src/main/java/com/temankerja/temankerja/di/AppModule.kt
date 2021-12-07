package com.temankerja.temankerja.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.temankerja.temankerja.data.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideQueryGetLowonganFromUser() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideUserPref(@ApplicationContext context: Context) : UserPreference = UserPreference(context)
}