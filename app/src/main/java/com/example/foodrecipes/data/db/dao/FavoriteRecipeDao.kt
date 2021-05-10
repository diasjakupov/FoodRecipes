package com.example.foodrecipes.data.db.dao

import androidx.room.*
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: FavoriteRecipe)

    @Query("SELECT * FROM favorite_recipe_table ORDER By title ASC")
    fun getRecipes(): Flow<List<FavoriteRecipe>>

    @Query("DELETE FROM favorite_recipe_table WHERE id=:id")
    suspend fun deleteRecipesById(id:Int)

    @Query("DELETE FROM favorite_recipe_table")
    suspend fun deleteAllRecipes()
}