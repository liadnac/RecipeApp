package com.example.recipeapp.data

import com.example.recipeapp.network.RecipeApiService

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}

class NetworkCategoryRepository(private val recipeApiService: RecipeApiService) : CategoryRepository {
    override suspend fun getCategories(): List<Category> = recipeApiService.getCategories()

}