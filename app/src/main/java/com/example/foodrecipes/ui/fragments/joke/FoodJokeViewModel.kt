package com.example.foodrecipes.ui.fragments.joke

import android.app.Application
import androidx.lifecycle.*
import com.example.foodrecipes.data.db.models.FoodJoke
import com.example.foodrecipes.data.repository.RepositoryImpl
import com.example.foodrecipes.data.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodJokeViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    application: Application
): AndroidViewModel(application){
    val foodJokeEntity: LiveData<List<FoodJoke>> = Transformations.switchMap(repository.foodJokeEntity){
        return@switchMap MutableLiveData(it.map { value-> value.foodJoke })
    }

    val foodJokeResponse: LiveData<NetworkResult<FoodJoke>> = Transformations.map(repository.foodJokeResponse){
        return@map it
    }

    fun getFoodJoke()=viewModelScope.launch {
        repository.getRandomFoodJokeSafeCall()
    }
}