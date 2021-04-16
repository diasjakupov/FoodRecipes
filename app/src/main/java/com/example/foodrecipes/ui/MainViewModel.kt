package com.example.foodrecipes.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.MyApplication
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.repository.RepositoryImpl
import com.example.foodrecipes.data.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val repositoryImpl: RepositoryImpl,
        application: Application
) : AndroidViewModel(application) {
    private var _recipesResponse: MutableLiveData<NetworkResult<FoodRecipeResponse>> = MutableLiveData()
    val recipeResponse:LiveData<NetworkResult<FoodRecipeResponse>>
        get() = _recipesResponse

    init {
       repositoryImpl.recipesResponse.observeForever{
           _recipesResponse.value=it
       }
    }



    fun getRecipes(queries:Map<String, String>)=viewModelScope.launch {
        repositoryImpl.getResponseSafeCall(queries)
    }





}