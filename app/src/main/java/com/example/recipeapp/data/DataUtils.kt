package com.example.recipeapp.data

import android.content.Context
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


fun getJsonDataFromAsset(
    context: Context,
    fileName: String
): String {
    val jsonString: String = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    return jsonString
}

fun categoryList(context: Context): MutableList<Category> {
    val jsonFileString = getJsonDataFromAsset(context = context, "category.json")
    val type = object : TypeToken<List<Category>>() {}.type
    return Gson().fromJson(jsonFileString, type)
}