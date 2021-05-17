package com.example.foodrecipes.data.db.models.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.data.db.models.FoodJoke


@Entity(tableName = "FOOD_JOKE")
data class FoodJokeEntity(
    @PrimaryKey(autoGenerate = false) val id:Int=0,
    @Embedded val foodJoke: FoodJoke
)