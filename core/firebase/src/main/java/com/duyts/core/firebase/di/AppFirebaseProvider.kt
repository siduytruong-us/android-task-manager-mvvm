package com.duyts.core.firebase.di

import com.duyts.core.firebase.AppFirebase
import com.duyts.core.firebase.AppFirebaseImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppFirebaseProvider {
	@Provides
	@Singleton
	fun providesFirebase(): AppFirebase = AppFirebaseImpl(Firebase.auth)
}