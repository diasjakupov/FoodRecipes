package com.example.foodrecipes.data.db.models.converters

import androidx.room.TypeConverter
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeTypeConverter {

    @TypeConverter
    fun recipeToString(recipe:FoodRecipeResponse):String{
        return Gson().toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(data:String): FoodRecipeResponse{
        return Gson().fromJson(data, object: TypeToken<FoodRecipeResponse>(){}.type)
    }
}