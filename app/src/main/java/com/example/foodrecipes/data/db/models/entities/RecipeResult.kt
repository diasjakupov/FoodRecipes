package com.example.foodrecipes.data.db.models.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.foodrecipes.data.db.models.ExtendedIngredient
import com.example.foodrecipes.data.db.models.converters.IngredientsTypeConverter


@Entity(tableName = "recipe_table")
@TypeConverters(IngredientsTypeConverter::class)
data class RecipeResult(
        val aggregateLikes: Int?,
        val cheap: Boolean?,
        val dairyFree: Boolean?,
        val extendedIngredients: List<ExtendedIngredient>?,
        val glutenFree: Boolean?,
        @PrimaryKey(autoGenerate = false) val id: Int,
        val image: String?,
        val readyInMinutes: Int?,
        val sourceName: String?,
        val sourceUrl: String?,
        val summary: String?,
        val title: String?,
        val vegan: Boolean?,
        val vegetarian: Boolean?,
        val veryHealthy: Boolean?
)