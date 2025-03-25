package sh.deut.recipeapp.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val subcategoryList: List<SubCategory>
)

@Serializable
data class SubCategory(
    val id: Int,
    val name: String,
)

