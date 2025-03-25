package com.example.recipeapp.fake

import com.example.recipeapp.data.Category
import com.example.recipeapp.data.CookTime
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.RecipePart
import com.example.recipeapp.data.SubCategory
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object FakeDataSource {
    val category1 = Category(
        1, "Test1", "", listOf(
            SubCategory(22, "subcategory1"), SubCategory(23, "subcategory2")
        )
    )
    val category2 = Category(
        2, "Test2", "", listOf(
            SubCategory(32, "subcategory3"), SubCategory(33, "subcategory4")
        )
    )
    val categoryList: List<Category> = listOf(category1, category2)

    val recipe1 = PartialRecipe(1, "RecipeTest1", "", 30.toDuration(DurationUnit.MINUTES), 22)
    val recipe2 = PartialRecipe(2, "RecipeTest2", "", 30.toDuration(DurationUnit.MINUTES), 22)

    val subcategoryRecipeList: List<PartialRecipe> = listOf(recipe1, recipe2)

    val recipePart: RecipePart = RecipePart("PartTest", listOf("milk", "eggs"), listOf("mix....", "cook...."))

    val fullRecipe: Recipe = Recipe(33, "FullRecipeTest", "", "", CookTime(30.toDuration(DurationUnit.MINUTES), 75.toDuration(DurationUnit.MINUTES)), listOf(
        recipePart), 1)
}