package com.example.recipeapp

import com.example.recipeapp.data.PartialRecipe
import org.junit.Assert.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DataUnitTest {
    @Test
    fun testRecipeSerialization() {
        val original: List<PartialRecipe> = listOf(PartialRecipe(1,"Test", 123.toDuration(DurationUnit.SECONDS),1), PartialRecipe(2,"Test2", 1234.toDuration(DurationUnit.SECONDS),1))
        val jsonString = Json.encodeToString(original)
        val deserialized = Json.decodeFromString<List<PartialRecipe>>(jsonString)

        assertEquals(original, deserialized)
    }

}
