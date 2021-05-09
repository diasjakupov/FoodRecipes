package com.example.foodrecipes.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import kotlinx.coroutines.flow.Flow

interface FavoriteRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: FavoriteRecipe)

    @Query("SELECT * FROM favorite_recipe_table ORDER By title ASC")
    fun getRecipes(): Flow<List<FavoriteRecipe>>

    @Delete
    suspend fun deleteRecipesById(item:FavoriteRecipe)

    @Query("DELETE FROM favorite_recipe_table")
    suspend fun deleteAllRecipes()
}