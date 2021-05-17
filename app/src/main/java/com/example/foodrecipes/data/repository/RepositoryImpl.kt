package com.example.foodrecipes.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.foodrecipes.data.datasource.LocalDataSource
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.datasource.RemoteDataSource
import com.example.foodrecipes.data.db.models.FoodJoke
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.db.models.entities.FoodJokeEntity
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.data.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class RepositoryImpl @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val InternetConnection: InternetConnection,
        private val localDataSource: LocalDataSource
) : Repository {

    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipeResponse>> = MutableLiveData()
    var searchedRecipesResponse: MutableLiveData<NetworkResult<FoodRecipeResponse>> = MutableLiveData()
    val recipeEntities: LiveData<List<RecipeResult>> = localDataSource.getRecipes().asLiveData()
    val foodJokeResponse=MutableLiveData<NetworkResult<FoodJoke>>()
    val foodJokeEntity: LiveData<List<FoodJokeEntity>> = localDataSource.getFoodJoke().asLiveData()
    val favoriteRecipeEntities: LiveData<List<FavoriteRecipe>> = localDataSource.getFavoriteRecipes().asLiveData()

    override suspend fun insertRecipes(entities: List<RecipeResult>) {
        localDataSource.deleteRecipes()
        localDataSource.insertRecipes(entities)
    }

    override suspend fun insertFavoriteRecipe(entity: FavoriteRecipe) {
        localDataSource.insertFavoriteRecipes(entity)
    }

    override suspend fun deleteFavoriteRecipe(id: Int) {
        localDataSource.deleteFavoriteRecipesById(id)
    }

    override suspend fun deleteAllFavoriteRecipes() {
        localDataSource.deleteFavoriteRecipes()
    }

    override suspend fun insertFoodJoke(foodJoke: FoodJokeEntity) {
        localDataSource.insertFoodJoke(foodJoke)
    }

    override suspend fun getResponseSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (InternetConnection.hasInternetConnection()) {
            try {
                val response = remoteDataSource.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)
                val foodRecipe=recipesResponse.value!!.data
                if(foodRecipe != null){
                    startRecipeCaching(foodRecipe)
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
                recipesResponse.value = NetworkResult.Error("Not Found")
            }

        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    override suspend fun searchRecipeSafeCall(queries: Map<String, String>) {
        searchedRecipesResponse.value = NetworkResult.Loading()
        if (InternetConnection.hasInternetConnection()) {
            try {
                val response = remoteDataSource.searchRecipes(queries)
                if(response.body()?.results?.size == 0){
                    searchedRecipesResponse.value= NetworkResult.Error("Not Found")
                }else{
                    searchedRecipesResponse.value = handleFoodRecipesResponse(response)
                }
            } catch (e: Exception) {
                searchedRecipesResponse.value = NetworkResult.Error("Not Found")
            }

        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    override suspend fun getRandomFoodJokeSafeCall() {
        foodJokeResponse.value=NetworkResult.Loading()
        if(InternetConnection.hasInternetConnection()){
            try{
                val response= remoteDataSource.getRandomJoke()
                foodJokeResponse.value=handleFoodJoke(response)
                val foodJoke=foodJokeResponse.value!!.data
                Log.e("TAG", "before caching")
                if(foodJoke!=null){
                    startJokeCaching(FoodJokeEntity(foodJoke = foodJoke))
                }
            }catch(e:Exception){
                Log.e("TAG", "${e.message} error")
                foodJokeResponse.value = NetworkResult.Error("ERROR")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleFoodJoke(response: Response<FoodJoke>):NetworkResult<FoodJoke> {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.code() == 402 -> {
                NetworkResult.Error("API key limited")
            }
            response.isSuccessful -> {
                NetworkResult.Success(response.body()!!)
            }

            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipeResponse>): NetworkResult<FoodRecipeResponse> {
        when {
            response.message().toString().contains("timeout") -> return NetworkResult.Error("Timeout")
            response.code() == 402 -> {
                return NetworkResult.Error("API key limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Not Found")
            }

            response.isSuccessful -> {
                return NetworkResult.Success(response.body()!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private suspend fun startJokeCaching(foodJoke: FoodJokeEntity){
        insertFoodJoke(foodJoke)
    }

    private suspend fun startRecipeCaching(foodRecipeResponse: FoodRecipeResponse){
        insertRecipes(foodRecipeResponse.results)
    }
}