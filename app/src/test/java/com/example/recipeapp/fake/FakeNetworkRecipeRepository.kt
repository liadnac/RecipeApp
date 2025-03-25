package com.example.recipeapp.fake

import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.RecipeRepository

class FakeNetworkRecipeRepository : RecipeRepository {
    override suspend fun getSelectedRecipe(recipeId: Int): Recipe {
        return FakeDataSource.fullRecipe
    }
}