package com.example.foodrecipes.data.repository

import com.example.foodrecipes.data.db.models.entities.RecipeResult

interface Repository {
    suspend fun getResponseSafeCall(queries:Map<String, String>)

    suspend fun insertRecipes(entities: List<RecipeResult>)
}