package sh.deut.recipeapp.fake

import sh.deut.recipeapp.data.Category
import sh.deut.recipeapp.data.PartialRecipe
import sh.deut.recipeapp.data.Recipe
import sh.deut.recipeapp.network.RecipeApiService

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