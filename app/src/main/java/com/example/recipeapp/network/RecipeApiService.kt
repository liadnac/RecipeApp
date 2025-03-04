package com.example.recipeapp.network

import com.example.recipeapp.data.Category
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.Recipe
import retrofit2.http.GET
import retrofit2.http.Path


interface RecipeApiService {
    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("subcategories/{subcategoryId}/recipes")
    suspend fun getSubcategoryRecipes(
        @Path("subcategoryId") subcategoryId: Int,
    ): List<PartialRecipe>

    @GET("recipes/{recipeId}")
    suspend fun getSelectedRecipe(
        @Path("recipeId") recipeId: Int,
    ): Recipe
}

