package com.example.foodrecipes.data.di

import android.content.Context
import com.example.foodrecipes.data.repository.DataStoreRepository
import com.example.foodrecipes.data.repository.DataStoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository{
        return DataStoreRepositoryImpl(context)
    }
}