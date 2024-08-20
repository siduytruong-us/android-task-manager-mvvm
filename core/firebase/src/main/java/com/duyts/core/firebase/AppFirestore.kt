package com.duyts.core.firebase

interface AppFirestore<T> {
	suspend fun create(item: T)
	suspend fun get(id: String): T?
	fun update(id: String)
	fun delete(id: String)
}


