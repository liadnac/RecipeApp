package sh.deut.recipeapp

import sh.deut.recipeapp.data.NetworkCategoryRepository
import sh.deut.recipeapp.fake.FakeDataSource
import sh.deut.recipeapp.fake.FakeRecipeApiService
import org.junit.Test
import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.runTest


class NetworkRecipeRepositoryTest {
    @Test
    fun networkCategoryRepository_getCategory_verifyCategoryList() {
        runTest {
            val repository = NetworkCategoryRepository(
                recipeApiService = FakeRecipeApiService()
            )
            assertEquals(FakeDataSource.categoryList, repository.getCategories())
        }

    }
}