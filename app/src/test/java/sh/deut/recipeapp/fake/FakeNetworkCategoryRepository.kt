package sh.deut.recipeapp.fake

import sh.deut.recipeapp.data.Category
import sh.deut.recipeapp.data.CategoryRepository

class FakeNetworkCategoryRepository: CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return FakeDataSource.categoryList
    }
}