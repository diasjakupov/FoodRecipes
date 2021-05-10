package com.example.foodrecipes.ui.activities.detail


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    application: Application
) : AndroidViewModel(application) {


    fun saveToFavorite(entity: FavoriteRecipe) {
        viewModelScope.launch {
            repository.insertFavoriteRecipe(entity)
        }
    }

    fun deleteFromFavoriteById(id:Int) {
        viewModelScope.launch {
            repository.deleteFavoriteRecipe(id)
        }
    }
    fun checkFavorites(entity: RecipeResult): LiveData<Boolean> {
        return Transformations.map(repository.favoriteRecipeEntities) {recipes->
            if(recipes.isNotEmpty()){
                return@map recipes.sortedBy { it.result.id }
                    .map { it.result.id }
                    .binarySearch(entity.id)>-1
            }else{
                return@map false
            }
        }
    }
}