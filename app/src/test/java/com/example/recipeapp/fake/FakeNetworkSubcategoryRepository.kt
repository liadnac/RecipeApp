package com.example.recipeapp.fake

import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.SubcategoryRepository

class FakeNetworkSubcategoryRepository: SubcategoryRepository {

    override suspend fun getSubcategoryRecipes(subcategoryId: Int): List<PartialRecipe> {
        return FakeDataSource.subcategoryRecipeList
    }
}