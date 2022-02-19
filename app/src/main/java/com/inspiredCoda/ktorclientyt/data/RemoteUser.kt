package com.inspiredCoda.ktorclientyt.data

import com.inspiredCoda.ktorclientyt.domain.data.User
import kotlinx.serialization.Serializable

@Serializable
data class RemoteUser(
    val name: String?,
    val email: String?,
    val phone: String?,
    val username: String
){
    fun toUser() = User(
        name = name ?: "",
        email = email ?: "",
        phone = phone ?: ""
    )
}
