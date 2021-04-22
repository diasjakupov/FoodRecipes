package com.example.foodrecipes.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.network.datasource.RemoteDataSource
import com.example.foodrecipes.data.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class RepositoryImpl @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val InternetConnection: InternetConnection
):Repository {
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipeResponse>> = MutableLiveData()

    override suspend fun getResponseSafeCall(queries: Map<String, String>) {
        recipesResponse.value=NetworkResult.Loading()
        if(InternetConnection.hasInternetConnection()){
            try {
                val response=remoteDataSource.getRecipes(queries)

                recipesResponse.value=handleFoodRecipesResponse(response)
            }catch (e: Exception){
                recipesResponse.value=NetworkResult.Error("Not Found")
            }

        }else{
            recipesResponse.value=NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipeResponse>): NetworkResult<FoodRecipeResponse> {
        when{
            response.message().toString().contains("timeout")-> return NetworkResult.Error("Timeout")
            response.code()==402->{
                return NetworkResult.Error("API key limited")
            }
            response.body()!!.results.isNullOrEmpty()->{
                return NetworkResult.Error("Not Found")
            }

            response.isSuccessful->{
                return NetworkResult.Success(response.body()!!)
            }

            else->{
                return NetworkResult.Error(response.message())
            }
        }
    }
}