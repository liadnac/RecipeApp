package com.example.recipeapp.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Category(
    val name: String,
    val subCategoryList: List<SubCategory>
)

@Serializable
data class SubCategory(
    val name: String,
)

fun getCategoryFromJsonFile(
    context: Context,
    fileName: String
): List<Category> {
    val jsonString: String = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return Json.decodeFromString<List<Category>>(jsonString)
}