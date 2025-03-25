package sh.deut.recipeapp.fake

import sh.deut.recipeapp.data.PartialRecipe
import sh.deut.recipeapp.data.SubcategoryRepository

class FakeNetworkSubcategoryRepository: SubcategoryRepository {

    override suspend fun getSubcategoryRecipes(subcategoryId: Int): List<PartialRecipe> {
        return FakeDataSource.subcategoryRecipeList
    }
}