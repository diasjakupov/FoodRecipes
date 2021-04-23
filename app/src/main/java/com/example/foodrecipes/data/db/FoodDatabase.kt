package com.example.foodrecipes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodrecipes.data.db.dao.RecipesDao
import com.example.foodrecipes.data.db.models.RecipeResult
import com.example.foodrecipes.data.db.models.converters.IngredientsTypeConverter
import com.example.foodrecipes.data.db.models.converters.RecipeTypeConverter


@Database(entities = [RecipeResult::class], version = 1, exportSchema = false)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun recipeDao():RecipesDao
}