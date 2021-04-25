package com.example.foodrecipes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodrecipes.data.db.dao.RecipesDao
import com.example.foodrecipes.data.db.models.entities.RecipeResult


@Database(entities = [RecipeResult::class], version = 1, exportSchema = false)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun recipeDao():RecipesDao
}