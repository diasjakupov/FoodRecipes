package com.example.foodrecipes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodrecipes.data.db.dao.FavoriteRecipeDao
import com.example.foodrecipes.data.db.dao.FoodJokeDao
import com.example.foodrecipes.data.db.dao.RecipesDao
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.db.models.entities.FoodJokeEntity
import com.example.foodrecipes.data.db.models.entities.RecipeResult


@Database(entities = [RecipeResult::class, FavoriteRecipe::class, FoodJokeEntity::class], version = 1, exportSchema = false)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun recipeDao():RecipesDao
    abstract fun favoriteRecipeDao():FavoriteRecipeDao
    abstract fun foodJoke():FoodJokeDao
}