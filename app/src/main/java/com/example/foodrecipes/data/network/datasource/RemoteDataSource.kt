package com.example.foodrecipes.data.network.datasource

import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.network.api.FoodRecipeApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val foodRecipeApi: FoodRecipeApi
) {
    suspend fun getRecipies(queries: Map<String, String>): Response<FoodRecipeResponse> {
        return foodRecipeApi.getRecipes(queries)
    }
}