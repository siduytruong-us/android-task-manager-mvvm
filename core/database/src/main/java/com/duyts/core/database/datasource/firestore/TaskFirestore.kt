package com.duyts.core.database.datasource.firestore

import android.util.Log
import com.duyts.core.database.datasource.firestore.entity.TaskFirestoreEntity
import com.duyts.core.datastore.auth.UserAuthenticationDataSource
import com.duyts.core.firebase.annotation.UserFirestoreCollection
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class TaskFirestore @Inject constructor(
	@UserFirestoreCollection private val userCollection: CollectionReference,
	private val userAuthDataStore: UserAuthenticationDataSource,
	private val firestore: FirebaseFirestore,
) {
	@OptIn(ExperimentalCoroutinesApi::class)
	suspend fun getTasks(): Flow<List<TaskFirestoreEntity>> =
		userAuthDataStore.data.filter { it.uid.isNotEmpty() }
			.map { it.uid }
			.flatMapLatest { uid ->
				flow {
					try {
						val tasks = userCollection.document(uid)
							.collection("tasks")
							.get()
							.await()
							.toObjects(TaskFirestoreEntity::class.java)
						emit(tasks)  // Emit the list of tasks
					} catch (e: Exception) {
						Log.d("DUYTS", e.message.orEmpty())
						emit(emptyList())  // Emit an empty list on error
					}
				}
			}


	suspend fun saveTask(item: TaskFirestoreEntity) =
		userAuthDataStore.data
			.filter { it.uid.isNotEmpty() }
			.map { it.uid }
			.firstOrNull()?.let { uid ->
				userCollection.document(uid)
					.collection("tasks")
					.add(item)
			}

	suspend fun updateAndDeleteTasks(
		tasksToUpdate: List<TaskFirestoreEntity>,
		tasksToDelete: List<TaskFirestoreEntity>,
	) {
		val userData = userAuthDataStore.data.firstOrNull() ?: return
		val taskDocument = userCollection.document(userData.uid)
			.collection("tasks")
		val batch = firestore.batch()

		// Updating tasks
		tasksToUpdate.forEach { task ->
			val docRef = taskDocument.document(task.id)
			batch.set(docRef, task) // Using set to update the document
		}

		// Deleting tasks
		tasksToDelete.forEach { task ->
			val docRef = taskDocument.document(task.id)
			batch.delete(docRef) // Using delete to remove the document
		}

		// Commit the batch operation
		try {
			batch.commit().await() // Ensure the batch operation is completed
		} catch (e: Exception) {
			Log.e("FirestoreDataSource", "Error updating and deleting tasks", e)
			throw e // Rethrow the exception if needed
		}
	}
}
