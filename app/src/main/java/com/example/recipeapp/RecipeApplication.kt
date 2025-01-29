package com.example.recipeapp

import android.app.Application
import com.example.recipeapp.data.AppContainer
import com.example.recipeapp.data.DefaultAppContainer

class RecipeApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}