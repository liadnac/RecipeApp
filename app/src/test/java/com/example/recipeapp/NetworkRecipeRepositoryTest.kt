package com.example.recipeapp

import com.example.recipeapp.data.NetworkCategoryRepository
import com.example.recipeapp.fake.FakeDataSource
import com.example.recipeapp.fake.FakeRecipeApiService
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