package sh.deut.recipeapp.network

import sh.deut.recipeapp.data.Category
import sh.deut.recipeapp.data.PartialRecipe
import sh.deut.recipeapp.data.Recipe
import retrofit2.http.GET
import retrofit2.http.Path


interface RecipeApiService {
    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("subcategories/{subcategoryId}/recipes")
    suspend fun getSubcategoryRecipes(
        @Path("subcategoryId") subcategoryId: Int,
    ): List<PartialRecipe>

    @GET("recipes/{recipeId}")
    suspend fun getSelectedRecipe(
        @Path("recipeId") recipeId: Int,
    ): Recipe
}

