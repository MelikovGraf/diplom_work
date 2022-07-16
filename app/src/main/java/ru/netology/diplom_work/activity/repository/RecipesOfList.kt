package ru.netology.diplom_work.activity.repository

import androidx.lifecycle.LiveData
import ru.netology.diplom_work.activity.Recipe

interface RecipesOfList {

    fun addRecipe(recipe: Recipe)

    fun deleteRecipe(recipe: Recipe)

    fun editRecipe(recipe: Recipe)

    fun getRecipe(recipeId: Int): Recipe

    fun getRecipeList(): LiveData<List<Recipe>>

    fun isFavorite(recipeId: Int)

    fun searchRecipe(request:String)

    companion object {
        const val CANCEL_SEARCH = "cancel search"
    }
}