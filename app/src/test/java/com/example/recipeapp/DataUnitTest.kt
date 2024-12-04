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
        val original: List<Recipe> = listOf(Recipe("Test", 123.toDuration(DurationUnit.SECONDS)), Recipe("Test2", 1234.toDuration(DurationUnit.SECONDS)))
        val jsonString = Json.encodeToString(original)
        val deserialized = Json.decodeFromString<List<Recipe>>(jsonString)

        assertEquals(original, deserialized)
    }

}
