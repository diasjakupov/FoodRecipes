package com.example.foodrecipes.data.db.models


import com.google.gson.annotations.SerializedName

data class FoodRecipeResponse(
    val results: List<Result>
)