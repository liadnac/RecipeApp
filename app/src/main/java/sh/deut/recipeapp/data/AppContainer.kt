package sh.deut.recipeapp.data

import sh.deut.recipeapp.network.RecipeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import sh.deut.recipeapp.BuildConfig

interface AppContainer {
    val categoryRepository: CategoryRepository
    val subcategoryRepository: SubcategoryRepository
    val recipeRepository: RecipeRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = BuildConfig.BASE_URL

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    val retrofitService : RecipeApiService by lazy {
        retrofit.create(RecipeApiService::class.java)
    }

    override val categoryRepository: CategoryRepository by lazy {
        NetworkCategoryRepository(retrofitService)
    }

    override val subcategoryRepository: SubcategoryRepository by lazy {
        NetworkSubcategoryRepository(retrofitService)
    }

    override val recipeRepository: RecipeRepository by lazy {
        NetworkRecipeRepository(retrofitService)
    }
}