package sh.deut.recipeapp.fake

import sh.deut.recipeapp.data.Recipe
import sh.deut.recipeapp.data.RecipeRepository

class FakeNetworkRecipeRepository : RecipeRepository {
    override suspend fun getSelectedRecipe(recipeId: Int): Recipe {
        return FakeDataSource.fullRecipe
    }
}