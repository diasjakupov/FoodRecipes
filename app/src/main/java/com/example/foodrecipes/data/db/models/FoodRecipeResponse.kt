package com.example.foodrecipes.data.db.models

import com.example.foodrecipes.data.db.models.entities.RecipeResult


data class FoodRecipeResponse(
        val results: List<RecipeResult>
)