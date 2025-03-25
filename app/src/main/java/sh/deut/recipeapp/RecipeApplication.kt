package sh.deut.recipeapp

import android.app.Application
import sh.deut.recipeapp.data.AppContainer
import sh.deut.recipeapp.data.DefaultAppContainer

class RecipeApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}