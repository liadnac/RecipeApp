package com.example.recipeapp

import androidx.compose.ui.platform.LocalContext
import com.example.recipeapp.data.Recipe
import org.junit.Assert.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DataUnitTest {
    @Test
    fun testRecipeSerialization() {
        val original: List<Recipe> = listOf(Recipe(1,"Test", 123.toDuration(DurationUnit.SECONDS),1), Recipe(2,"Test2", 1234.toDuration(DurationUnit.SECONDS),1))
        val jsonString = Json.encodeToString(original)
        val deserialized = Json.decodeFromString<List<Recipe>>(jsonString)

        assertEquals(original, deserialized)
    }

}
