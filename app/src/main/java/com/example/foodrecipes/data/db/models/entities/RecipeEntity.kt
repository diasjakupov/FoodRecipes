package com.example.foodrecipes.data.db.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.data.db.models.FoodRecipeResponse


class RecipeEntity(
        val foodRecipe: FoodRecipeResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int=0
}