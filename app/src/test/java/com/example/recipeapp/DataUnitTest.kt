package com.example.recipeapp

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
        val original = Recipe("Test", 123.toDuration(DurationUnit.SECONDS))
        val jsonString = Json.encodeToString(original)
        val deserialized = Json.decodeFromString<Recipe>(jsonString)

        assertEquals(original, deserialized)
    }
}
