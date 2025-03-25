package sh.deut.recipeapp.data

import sh.deut.recipeapp.network.RecipeApiService

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}

class NetworkCategoryRepository(private val recipeApiService: RecipeApiService) : CategoryRepository {
    override suspend fun getCategories(): List<Category> = recipeApiService.getCategories()

}