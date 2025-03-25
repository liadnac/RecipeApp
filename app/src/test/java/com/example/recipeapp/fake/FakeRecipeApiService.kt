package com.example.recipeapp.fake

import com.example.recipeapp.data.Category
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.network.RecipeApiService

class FakeRecipeApiService: RecipeApiService {
    override suspend fun getCategories(): List<Category> {
        return FakeDataSource.categoryList
    }

    override suspend fun getSubcategoryRecipes(subcategoryId: Int): List<PartialRecipe> {
        return FakeDataSource.subcategoryRecipeList
    }

    override suspend fun getSelectedRecipe(recipeId: Int): Recipe {
        return FakeDataSource.fullRecipe
    }
}