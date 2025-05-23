package sh.deut.recipeapp.fake

import sh.deut.recipeapp.data.Category
import sh.deut.recipeapp.data.CookTime
import sh.deut.recipeapp.data.PartialRecipe
import sh.deut.recipeapp.data.Recipe
import sh.deut.recipeapp.data.RecipePart
import sh.deut.recipeapp.data.SubCategory
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

    val selectedSubCategory = SubCategory(22, "subcategory1")

    val recipe1 = PartialRecipe(1, "RecipeTest1", "", 30.toDuration(DurationUnit.MINUTES), 22)
    val recipe2 = PartialRecipe(2, "RecipeTest2", "", 30.toDuration(DurationUnit.MINUTES), 22)

    val subcategoryRecipeList: List<PartialRecipe> = listOf(recipe1, recipe2)

    val recipePart: RecipePart =
        RecipePart("PartTest", listOf("milk", "eggs"), listOf("mix....", "cook...."))

    val fullRecipeBasedOnRecipe1: Recipe = Recipe(
        recipe1.id,
        recipe1.name,
        recipe1.imgUrl,
        "",
        CookTime(15.toDuration(DurationUnit.MINUTES), recipe1.cookTime),
        recipeParts = listOf(recipePart),
        recipe1.subcategoryId
    )

    val fullRecipe: Recipe = Recipe(
        33,
        "FullRecipeTest",
        "",
        "",
        CookTime(30.toDuration(DurationUnit.MINUTES), 75.toDuration(DurationUnit.MINUTES)),
        listOf(recipePart),
        1
    )
}