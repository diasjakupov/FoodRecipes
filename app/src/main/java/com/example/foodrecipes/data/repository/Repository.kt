package com.example.foodrecipes.data.repository

import com.example.foodrecipes.data.db.models.RecipeResult
import com.example.foodrecipes.data.db.models.entities.RecipeEntity

interface Repository {
    suspend fun getResponseSafeCall(queries:Map<String, String>)

    suspend fun insertRecipes(recipeEntity: List<RecipeResult>)
}