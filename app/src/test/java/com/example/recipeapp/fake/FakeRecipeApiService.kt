package com.example.recipeapp.fake

import com.example.recipeapp.data.Category
import com.example.recipeapp.network.RecipeApiService

class FakeRecipeApiService: RecipeApiService {
    override suspend fun getCategories(): List<Category> {
        return FakeDataSource.categoryList
    }
}