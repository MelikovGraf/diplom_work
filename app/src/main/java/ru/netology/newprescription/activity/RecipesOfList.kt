package ru.netology.newprescription.activity

import androidx.lifecycle.LiveData

interface RecipesOfList {

    fun addRecipe(recipe: Recipe)

    fun deleteRecipe(recipe: Recipe)

    fun editRecipe(recipe: Recipe)

    fun getRecipe(recipeId: Int): Recipe

    fun getRecipeList(): LiveData<List<Recipe>>

}