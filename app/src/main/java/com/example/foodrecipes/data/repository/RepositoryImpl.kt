package com.example.foodrecipes.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.foodrecipes.data.datasource.LocalDataSource
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.datasource.RemoteDataSource
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.data.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
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

    override suspend fun insertRecipes(entities: List<RecipeResult>) {
        localDataSource.deleteRecipes()
        localDataSource.insertRecipes(entities)
    }

    override suspend fun getResponseSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (InternetConnection.hasInternetConnection()) {
            try {
                val response = remoteDataSource.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)
                val foodRecipe=recipesResponse.value!!.data
                if(foodRecipe != null){
                    startCaching(foodRecipe)
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
                Log.e("TAG", "from searching ${response.body()}")
                if(response.body()?.results?.size == 0){
                    Log.e("TAG", "size equals to zero")
                    searchedRecipesResponse.value= NetworkResult.Error("Not Found")
                }else{
                    Log.e("TAG", "size doesn't equal to zero")
                    searchedRecipesResponse.value = handleFoodRecipesResponse(response)
                }
            } catch (e: Exception) {
                searchedRecipesResponse.value = NetworkResult.Error("Not Found")
            }

        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection")
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

    private suspend fun startCaching(foodRecipeResponse: FoodRecipeResponse){
        Log.e("TAG", "start caching ${foodRecipeResponse.results.map { it.title }}")
        insertRecipes(foodRecipeResponse.results)
    }
}