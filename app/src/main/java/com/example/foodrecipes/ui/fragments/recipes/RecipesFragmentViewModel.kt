package com.example.foodrecipes.ui.fragments.recipes

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.data.repository.DataStoreRepository
import com.example.foodrecipes.data.repository.RepositoryImpl
import com.example.foodrecipes.data.utils.Constant.ADD_RECIPE_INFO
import com.example.foodrecipes.data.utils.Constant.DEFAULT_DIET_TYPE
import com.example.foodrecipes.data.utils.Constant.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.data.utils.Constant.DEFAULT_NUMBER

import com.example.foodrecipes.data.utils.Constant.DIET_TYPE
import com.example.foodrecipes.data.utils.Constant.FILL_ING
import com.example.foodrecipes.data.utils.Constant.MEAL_TYPE
import com.example.foodrecipes.data.utils.Constant.NUMBER_OF_RECIPES
import com.example.foodrecipes.data.utils.Constant.SEARCH

import com.example.foodrecipes.data.utils.NetworkResult
import com.example.foodrecipes.data.utils.observeOnce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class RecipesFragmentViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl,
    private val dataStoreRepository: DataStoreRepository,
    application: Application
) : AndroidViewModel(application) {
    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus=false
    var backOnline=false


    val recipeResponse: LiveData<NetworkResult<FoodRecipeResponse>> =
        Transformations.map(repositoryImpl.recipesResponse) {
            return@map it
        }

    val searchedRecipesResponse: LiveData<NetworkResult<FoodRecipeResponse>> =
        Transformations.map(repositoryImpl.searchedRecipesResponse) {
            return@map it
        }


    val recipeEntities: LiveData<List<RecipeResult>> =
        Transformations.map(repositoryImpl.recipeEntities) {
        return@map it
    }


    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readOnlineStatus = dataStoreRepository.readOnlineStatus.asLiveData()



    suspend fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[NUMBER_OF_RECIPES] = DEFAULT_NUMBER
        queries[MEAL_TYPE] = readMealAndDietType.first().selectedMealType
        queries[FILL_ING] = "true"
        queries[ADD_RECIPE_INFO] = "true"
        queries[DIET_TYPE] = readMealAndDietType.first().selectedDietType
        Log.e("TAG", "apply queries2")
        return queries
    }

    fun applySearchQueries(search:String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[NUMBER_OF_RECIPES] = DEFAULT_NUMBER
        queries[MEAL_TYPE] = mealType
        queries[SEARCH] = search.toLowerCase(Locale.ROOT)
        queries[FILL_ING] = "true"
        queries[ADD_RECIPE_INFO] = "true"
        queries[DIET_TYPE] = dietType
        return queries
    }

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        repositoryImpl.getResponseSafeCall(queries)
    }

    fun searchRecipes(queries: Map<String, String>)=viewModelScope.launch {
        Log.e("TAG", "request search")
        repositoryImpl.searchRecipeSafeCall(queries)
    }

    suspend fun saveMealAndDietType(
        mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int
    ) = withContext(viewModelScope.coroutineContext) {
        dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
    }

    private fun saveBackOnline(backOnline:Boolean)=viewModelScope.launch(Dispatchers.IO){
        dataStoreRepository.saveBackOnlineStatus(backOnline)
    }

    fun showNetworkStatus(){
        if(!networkStatus){
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        }else if(networkStatus){
            if(backOnline){
                Toast.makeText(getApplication(), "We're back online", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }


}