package com.example.foodrecipes.data.db.models.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipe_table")
data class FavoriteRecipe(
    @PrimaryKey(autoGenerate = true)
    var item_id: Int=0,
    @Embedded var result: RecipeResult
)