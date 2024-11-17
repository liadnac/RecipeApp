package com.example.recipeapp.data

data class Category(
    val name: String,
    val subCategoryList: List<SubCategory>
)

data class SubCategory(
    val name: String,
)