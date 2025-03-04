package com.example.recipeapp

import com.example.recipeapp.data.CookTime
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.RecipePart
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DataUnitTest {
    @Test
    fun testPartialRecipeSerialization() {
        val original: List<PartialRecipe> = listOf(
            PartialRecipe(1, "Test", "", 123.toDuration(DurationUnit.SECONDS), 1),
            PartialRecipe(2, "Test2", "", 1234.toDuration(DurationUnit.SECONDS), 1)
        )
        val jsonString = Json.encodeToString(original)
        val deserialized = Json.decodeFromString<List<PartialRecipe>>(jsonString)

        assertEquals(original, deserialized)
    }

    @Test
    fun testRecipeSerialization() {
        val recipePart = RecipePart(
            name = "Test2",
            ingredients = listOf("eggs", "flour"),
            instructions = listOf("Add flour and eggs")
        )
        val cookTime = CookTime(
            handsOn = 30.toDuration(DurationUnit.MINUTES),
            readyIn = 100.toDuration(DurationUnit.MINUTES)
        )
        val original = Recipe(
            id = 1, name = "Test",
            cookTime = cookTime,
            recipeParts = listOf(recipePart),
            subcategoryId = 1,
            description = "desc",
            imgUrl = ""
        )
        val jsonString = Json.encodeToString(original)
        val deserialized = Json.decodeFromString<Recipe>(jsonString)

        assertEquals(original, deserialized)
    }

}
