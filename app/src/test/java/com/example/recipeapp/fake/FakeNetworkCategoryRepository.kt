package com.example.recipeapp.fake

import com.example.recipeapp.data.Category
import com.example.recipeapp.data.CategoryRepository

class FakeNetworkCategoryRepository: CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return FakeDataSource.categoryList
    }
}