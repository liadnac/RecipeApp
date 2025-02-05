package com.example.recipeapp.data

import com.example.recipeapp.network.RecipeApiService

interface RecipeRepository {
    suspend fun getSelectedRecipe(recipeId: Int): Recipe
}

class NetworkRecipeRepository(private val recipeApiService: RecipeApiService): RecipeRepository {
    override suspend fun getSelectedRecipe(recipeId: Int): Recipe = recipeApiService.getSelectedRecipe(
        recipeId = recipeId
    )
}