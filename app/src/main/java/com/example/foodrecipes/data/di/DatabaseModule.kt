package com.example.foodrecipes.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodrecipes.data.db.FoodDatabase
import com.example.foodrecipes.data.db.dao.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
            @ApplicationContext context: Context
    )= Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            "food_database"
    ).build()

    @Singleton
    @Provides
    fun provideRecipeDao(database:FoodDatabase): RecipesDao{
        return database.recipeDao()
    }
}