package com.example.foodrecipes.data.datasource

import android.util.Log
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.network.api.FoodRecipeApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val foodRecipeApi: FoodRecipeApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipeResponse> {
        Log.e("TAG", "queries $queries")
        return foodRecipeApi.getRecipes(queries)
    }

    suspend fun searchRecipes(queries: Map<String, String>): Response<FoodRecipeResponse> {
        Log.e("TAG", "queries $queries")
        return foodRecipeApi.searchRecipes(queries)
    }
}