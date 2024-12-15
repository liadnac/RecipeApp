package com.example.recipeapp.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Category(
    val id: Int,
    val name: String
)

@Serializable
data class SubCategory(
    val id: Int,
    val name: String,
    val categoryId: Int,
)

fun getCategoriesFromJsonFile(
    context: Context,
    fileName: String
): List<Category> {
    val jsonString: String = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return Json.decodeFromString<List<Category>>(jsonString)
}

fun getSubcategoriesFromJsonFile(
    context: Context,
    fileName: String
): List<SubCategory> {
    val jsonString: String = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return Json.decodeFromString<List<SubCategory>>(jsonString)
}