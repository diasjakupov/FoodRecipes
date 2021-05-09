package com.example.foodrecipes.data.datasource

import android.util.Log
import androidx.lifecycle.asLiveData
import com.example.foodrecipes.data.db.dao.FavoriteRecipeDao
import com.example.foodrecipes.data.db.dao.RecipesDao
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.javaType
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue
import kotlin.reflect.jvm.javaType

class LocalDataSource @Inject constructor(
        private val recipesDao: RecipesDao,
        private val favoriteDao: FavoriteRecipeDao
) {

    suspend fun insertRecipes(recipes: List<RecipeResult>){
        Log.e("TAG", "start caching local ${recipes.map { it.title }}")
        recipesDao.insert(recipes)
    }

    suspend fun deleteRecipes(){
        recipesDao.deleteRecipes()
    }

    fun getRecipes(): Flow<List<RecipeResult>>{
        return recipesDao.getRecipes()
    }


    suspend fun insertFavoriteRecipes(recipe: FavoriteRecipe){
        favoriteDao.insert(recipe)
    }

    suspend fun deleteFavoriteRecipes(){
        favoriteDao.deleteAllRecipes()
    }

    suspend fun deleteFavoriteRecipesByEntity(recipe: FavoriteRecipe){
        favoriteDao.deleteRecipesById(recipe)
    }

    fun getFavoriteRecipes(): Flow<List<FavoriteRecipe>>{
        return favoriteDao.getRecipes()
    }
}