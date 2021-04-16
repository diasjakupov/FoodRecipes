package com.example.foodrecipes.data.network.api

import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipeApi {

    @GET("recipes/complexSearch")
    suspend fun getRecipes(
            @QueryMap queries:Map<String, String>):Response<FoodRecipeResponse>
}