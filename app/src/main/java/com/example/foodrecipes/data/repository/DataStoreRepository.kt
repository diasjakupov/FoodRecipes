package com.example.foodrecipes.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveMealAndDietType(
        mealType:String, mealTypeId:Int,dietType:String, dietTypeId:Int
    )

    suspend fun saveBackOnlineStatus(backOnline:Boolean)

    val readMealAndDietType:Flow<MealAndDietType>
    val readOnlineStatus:Flow<Boolean>
}