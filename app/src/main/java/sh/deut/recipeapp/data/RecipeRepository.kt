package sh.deut.recipeapp.data

import sh.deut.recipeapp.network.RecipeApiService

interface RecipeRepository {
    suspend fun getSelectedRecipe(recipeId: Int): Recipe
}

class NetworkRecipeRepository(private val recipeApiService: RecipeApiService): RecipeRepository {
    override suspend fun getSelectedRecipe(recipeId: Int): Recipe = recipeApiService.getSelectedRecipe(
        recipeId = recipeId
    )
}