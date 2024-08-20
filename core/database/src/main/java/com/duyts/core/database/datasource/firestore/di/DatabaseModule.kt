package com.duyts.core.database.datasource.firestore.di

import android.content.Context
import androidx.room.Room
import com.duyts.core.database.datasource.firestore.TaskFirestore
import com.duyts.core.database.datasource.local.AppDatabase
import com.duyts.core.datastore.auth.UserAuthenticationDataSource
import com.duyts.core.firebase.annotation.UserFirestoreCollection
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FirestoreModule {
	@Provides
	@Singleton
	fun providesTaskFirestore(
		@UserFirestoreCollection userCollection: CollectionReference,
		userAuthDataStore: UserAuthenticationDataSource,
		firebaseFirestore: FirebaseFirestore,
	): TaskFirestore = TaskFirestore(userCollection, userAuthDataStore, firebaseFirestore)
}