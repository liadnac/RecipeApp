package sh.deut.recipeapp.data

import sh.deut.recipeapp.network.RecipeApiService

interface SubcategoryRepository {
    suspend fun getSubcategoryRecipes(subcategoryId: Int): List<PartialRecipe>
}

class NetworkSubcategoryRepository(private val recipeApiService: RecipeApiService) : SubcategoryRepository {
    override suspend fun getSubcategoryRecipes(subcategoryId: Int): List<PartialRecipe> = recipeApiService.getSubcategoryRecipes(
        subcategoryId = subcategoryId,
    )

}