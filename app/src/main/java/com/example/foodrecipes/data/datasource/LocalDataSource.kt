package com.example.foodrecipes.data.datasource

import android.util.Log
import com.example.foodrecipes.data.db.dao.RecipesDao
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
        private val recipesDao: RecipesDao
) {
    suspend fun insertRecipes(recipes: List<RecipeResult>){
        Log.e("TAG", "${recipes.size}")
        recipesDao.insert(recipes)
    }

    fun getRecipes(): Flow<List<RecipeResult>>{
        return recipesDao.getRecipes()
    }
}