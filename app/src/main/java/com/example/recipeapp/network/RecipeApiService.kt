package com.example.recipeapp.network

import com.example.recipeapp.data.Category
import retrofit2.http.GET


interface RecipeApiService {
    @GET("categories/categories.json")
    suspend fun getCategories(): List<Category>
}

