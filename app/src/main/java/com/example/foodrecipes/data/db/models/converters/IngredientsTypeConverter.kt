package com.example.foodrecipes.data.db.models.converters

import androidx.room.TypeConverter
import com.example.foodrecipes.data.db.models.ExtendedIngredient
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredientsTypeConverter {

    @TypeConverter
    fun ingredientsToString(recipe: List<ExtendedIngredient>):String{
        return Gson().toJson(recipe)
    }

    @TypeConverter
    fun ingredientsToRecipe(data:String): List<ExtendedIngredient> {
        return Gson().fromJson(data, object: TypeToken<List<ExtendedIngredient>>(){}.type)
    }
}