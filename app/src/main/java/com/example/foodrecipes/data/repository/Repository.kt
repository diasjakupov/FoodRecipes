package com.example.foodrecipes.data.repository

import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.db.models.entities.FoodJokeEntity
import com.example.foodrecipes.data.db.models.entities.RecipeResult

interface Repository {
    suspend fun getResponseSafeCall(queries:Map<String, String>)
    suspend fun searchRecipeSafeCall(queries:Map<String, String>)
    suspend fun getRandomFoodJokeSafeCall()
    suspend fun insertRecipes(entities: List<RecipeResult>)
    suspend fun insertFavoriteRecipe(entity: FavoriteRecipe)
    suspend fun deleteFavoriteRecipe(id: Int)
    suspend fun deleteAllFavoriteRecipes()
    suspend fun insertFoodJoke(foodJoke: FoodJokeEntity)
}