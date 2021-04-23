package com.example.foodrecipes.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodrecipes.data.db.models.RecipeResult
import com.example.foodrecipes.data.db.models.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipes:List<RecipeResult>)

    @Query("SELECT * FROM recipe_table ORDER By id ASC")
    fun getRecipes():Flow<List<RecipeResult>>
}