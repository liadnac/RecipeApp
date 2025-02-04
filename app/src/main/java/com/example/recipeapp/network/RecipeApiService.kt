package com.example.recipeapp.network

import com.example.recipeapp.data.Category
import com.example.recipeapp.data.PartialRecipe
import retrofit2.http.GET
import retrofit2.http.Path


interface RecipeApiService {
    @GET("categories/content.json")
    suspend fun getCategories(): List<Category>

    @GET("/subcategories/{subcategoryId}/recipes/content.json")
    suspend fun getSubcategoryRecipes(
        @Path("subcategoryId") subcategoryId: Int,
    ): List<PartialRecipe>
}

