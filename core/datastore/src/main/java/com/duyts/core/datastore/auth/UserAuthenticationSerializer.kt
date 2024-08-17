package com.duyts.core.datastore.auth

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.duyts.tasks.core.datastore.UserAuthentication
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserAuthenticationSerializer @Inject constructor() : Serializer<UserAuthentication> {
	override val defaultValue: UserAuthentication
		get() = UserAuthentication.getDefaultInstance()

	override suspend fun readFrom(input: InputStream): UserAuthentication =
		try {
			UserAuthentication.parseFrom(input)
		} catch (exception: InvalidProtocolBufferException) {
			throw CorruptionException("Cannot read proto: ", exception)
		}

	override suspend fun writeTo(t: UserAuthentication, output: OutputStream) {
		t.writeTo(output)
	}

}