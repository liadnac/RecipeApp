package sh.deut.recipeapp

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import sh.deut.recipeapp.data.NetworkCategoryRepository
import sh.deut.recipeapp.data.NetworkRecipeRepository
import sh.deut.recipeapp.data.NetworkSubcategoryRepository
import sh.deut.recipeapp.fake.FakeDataSource
import sh.deut.recipeapp.fake.FakeRecipeApiService


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

    @Test
    fun networkSubcategoryRepository_getSubcategoryRecipes_verifyRecipeList() {
        runTest {
            val repository = NetworkSubcategoryRepository(
                recipeApiService = FakeRecipeApiService()
            )
            assertEquals(
                FakeDataSource.subcategoryRecipeList, repository.getSubcategoryRecipes(
                    subcategoryId = 22
                )
            )
        }
    }

    @Test
    fun networkRecipeRepository_getSelectedRecipe_verifyRecipe() {
        runTest {
            val repository = NetworkRecipeRepository(
                recipeApiService = FakeRecipeApiService()
            )
            assertEquals(
                FakeDataSource.fullRecipe, repository.getSelectedRecipe(
                    recipeId = 33
                )
            )
        }
    }
}