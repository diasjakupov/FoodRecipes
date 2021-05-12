package com.example.foodrecipes.data.db.models.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.foodrecipes.data.db.models.ExtendedIngredient
import com.example.foodrecipes.data.db.models.converters.IngredientsTypeConverter

@Entity(tableName = "favorite_recipe_table")
@TypeConverters(IngredientsTypeConverter::class)
data class FavoriteRecipe(
    @Embedded var result: RecipeResult
){
    @PrimaryKey(autoGenerate = true) var itemId=0
}