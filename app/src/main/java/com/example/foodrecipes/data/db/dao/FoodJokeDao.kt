package com.example.foodrecipes.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodrecipes.data.db.models.entities.FoodJokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodJokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(joke:FoodJokeEntity)


    @Query("SELECT * FROM FOOD_JOKE ORDER BY id ASC")
    fun getFoodJoke(): Flow<List<FoodJokeEntity>>
}