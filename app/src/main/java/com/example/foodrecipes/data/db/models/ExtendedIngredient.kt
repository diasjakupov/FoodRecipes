package com.example.foodrecipes.data.db.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ExtendedIngredient(
    val aisle: String?,
    val amount: Double?,
    val consistency: String?,
    val image: String?,
    val name: String?,
    val original: String?,
    val unit: String
): Parcelable