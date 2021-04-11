package com.example.foodrecipes.data.db.models


import com.google.gson.annotations.SerializedName

data class ExtendedIngredient(
    val aisle: String,
    val amount: Int,
    val consistency: String,
    val image: String,
    val name: String,
    val original: String,
    val unit: String
)