package com.example.recipeapp.data

data class Category(
    val id: Long,
    val name: String,
    val subCategoryList: List<SubCategory>
)

data class SubCategory(
    val id: Long,
    val name: String,
    val categoryId: Long
)