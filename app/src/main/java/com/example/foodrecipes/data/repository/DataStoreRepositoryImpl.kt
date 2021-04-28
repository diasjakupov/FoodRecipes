package com.example.foodrecipes.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodrecipes.data.utils.Constant.DEFAULT_DIET_TYPE
import com.example.foodrecipes.data.utils.Constant.DEFAULT_MEAL_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context):
    DataStoreRepository
{
    private object PreferencesKeys{
        val selectedMealType= stringPreferencesKey("MEAL_TYPE")
        val selectedMealTypeId= intPreferencesKey("MEAL_TYPE_ID")
        val selectedDietType= stringPreferencesKey("DIET_TYPE")
        val selectedDietTypeId= intPreferencesKey("DIET_TYPE_ID")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "foody_preferences")

    override suspend fun saveMealAndDietType(
        mealType:String, mealTypeId:Int,dietType:String, dietTypeId:Int
    ){
        context.dataStore.edit {preferences->
            preferences[PreferencesKeys.selectedMealType]=mealType
            preferences[PreferencesKeys.selectedMealTypeId]=mealTypeId
            preferences[PreferencesKeys.selectedDietType]=dietType
            preferences[PreferencesKeys.selectedDietTypeId]=dietTypeId
        }
    }

    override val readMealAndDietType:Flow<MealAndDietType> = context.dataStore.data
        .catch {exc->
        if(exc is IOException){
            emit(emptyPreferences())
        }else{
            throw exc
        }
    }
        .map { preferences ->
        val selectedMealType = preferences[PreferencesKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
        val selectedMealTypeId = preferences[PreferencesKeys.selectedMealTypeId] ?: 0
        val selectedDietType = preferences[PreferencesKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
        val selectedDietTypeId = preferences[PreferencesKeys.selectedDietTypeId] ?: 0
        MealAndDietType(
            selectedMealType,
            selectedMealTypeId,
            selectedDietType,
            selectedDietTypeId
        )
    }
}
