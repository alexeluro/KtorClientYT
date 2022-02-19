package com.inspiredCoda.ktorclientyt.data

interface Repository {

    suspend fun getUser(): NetworkEvents

}