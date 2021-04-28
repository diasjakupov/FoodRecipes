package com.example.foodrecipes.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveMealAndDietType(
        mealType:String, mealTypeId:Int,dietType:String, dietTypeId:Int
    )

    val readMealAndDietType:Flow<MealAndDietType>
}