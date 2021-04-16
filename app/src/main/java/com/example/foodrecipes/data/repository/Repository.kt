package com.example.foodrecipes.data.repository

interface Repository {
    suspend fun getResponseSafeCall(queries:Map<String, String>)
}