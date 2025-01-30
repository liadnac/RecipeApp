package com.example.recipeapp.fake

import com.example.recipeapp.data.Category
import com.example.recipeapp.data.SubCategory

object FakeDataSource {
    val category1 = Category(
        1, "Test1", listOf(
            SubCategory(22, "subcategory1"), SubCategory(23, "subcategory2")
        )
    )
    val category2 = Category(
        2, "Test2", listOf(
            SubCategory(32, "subcategory3"), SubCategory(33, "subcategory4")
        )
    )
    val categoryList: List<Category> = listOf(category1, category2)
}