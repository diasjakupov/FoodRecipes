package com.example.foodrecipes.data.di

import com.example.foodrecipes.MyApplication
import com.example.foodrecipes.data.BASE_URL
import com.example.foodrecipes.data.network.api.FoodRecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
            okhttp:OkHttpClient,
            gson:GsonConverterFactory
    ):Retrofit{
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okhttp)
                .addConverterFactory(gson)
                .build()
    }


    @Singleton
    @Provides
    fun provideFoodRecipeApi(retrofit:Retrofit):FoodRecipeApi{
        return retrofit.create(FoodRecipeApi::class.java)
    }
}