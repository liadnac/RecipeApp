package com.example.recipeapp.data

import com.example.recipeapp.network.RecipeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val categoryRepository: CategoryRepository
    val subcategoryRepository: SubcategoryRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:8000"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    val retrofitService : RecipeApiService by lazy {
        retrofit.create(RecipeApiService::class.java)
    }

    override val categoryRepository: CategoryRepository by lazy {
        NetworkCategoryRepository(retrofitService)
    }

    override val subcategoryRepository: SubcategoryRepository by lazy {
        NetworkSubcategoryRepository(retrofitService)
    }
}