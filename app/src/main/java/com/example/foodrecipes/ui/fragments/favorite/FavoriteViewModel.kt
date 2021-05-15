package com.example.foodrecipes.ui.fragments.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    application: Application
) : AndroidViewModel(application){
    private var _favoriteEntities: LiveData<List<FavoriteRecipe>> =
        Transformations.map(repository.favoriteRecipeEntities){
            return@map it
        }
    val favoriteEntities get() = _favoriteEntities

    fun deleteFromFavoriteById(id:Int) {
        viewModelScope.launch {
            repository.deleteFavoriteRecipe(id)
        }
    }

    fun deleteAllFavorite() {
        viewModelScope.launch {
            repository.deleteAllFavoriteRecipes()
        }
    }
}