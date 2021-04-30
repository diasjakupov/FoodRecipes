package com.example.foodrecipes.data.db.models.converters

import android.util.Log
import androidx.room.TypeConverter
import com.example.foodrecipes.data.db.models.ExtendedIngredient
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredientsTypeConverter {

    @TypeConverter
    fun ingredientsToString(ingredients: List<ExtendedIngredient>?):String{
        return Gson().toJson(ingredients)
    }

    @TypeConverter
    fun ingredientsToRecipe(data:String): List<ExtendedIngredient>? {
        return Gson().fromJson(data, object: TypeToken<List<ExtendedIngredient>?>(){}.type)
    }
}