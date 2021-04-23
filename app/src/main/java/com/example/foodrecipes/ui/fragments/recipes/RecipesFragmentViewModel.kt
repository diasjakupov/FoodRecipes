package com.example.foodrecipes.ui.fragments.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.db.models.RecipeResult
import com.example.foodrecipes.data.db.models.entities.RecipeEntity
import com.example.foodrecipes.data.repository.RepositoryImpl
import com.example.foodrecipes.data.utils.Constant.ADD_RECIPE_INFO
import com.example.foodrecipes.data.utils.Constant.DIET
import com.example.foodrecipes.data.utils.Constant.FILL_ING
import com.example.foodrecipes.data.utils.Constant.NUMBER_OF_RECIPES
import com.example.foodrecipes.data.utils.Constant.TYPE
import com.example.foodrecipes.data.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesFragmentViewModel @Inject constructor(
        private val repositoryImpl: RepositoryImpl,
        application: Application
) : AndroidViewModel(application) {
    private var _recipesResponse: MutableLiveData<NetworkResult<FoodRecipeResponse>> = MutableLiveData()
    val recipeResponse: LiveData<NetworkResult<FoodRecipeResponse>>
        get() = _recipesResponse

    private var _recipeEntities: MutableLiveData<List<RecipeResult>> = MutableLiveData()
    val recipeEntities: LiveData<List<RecipeResult>>
        get() = _recipeEntities

    init {
        repositoryImpl.apply {
            recipesResponse.observeForever{
                _recipesResponse.value=it
            }
            recipeEntities.observeForever{
                _recipeEntities.value=it
            }
        }
    }


    fun applyQueries():HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[NUMBER_OF_RECIPES]="50"
        queries[TYPE]="snack"
        queries[FILL_ING]="true"
        queries[ADD_RECIPE_INFO]="true"
        queries[DIET]="true"
        return queries
    }

    fun getRecipes(queries:Map<String, String>)=viewModelScope.launch {
        repositoryImpl.getResponseSafeCall(queries)
    }

}