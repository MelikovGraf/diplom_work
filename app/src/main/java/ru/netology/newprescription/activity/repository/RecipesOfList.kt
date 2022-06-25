package ru.netology.newprescription.activity.repository

import androidx.lifecycle.LiveData
import ru.netology.newprescription.activity.Recipe

interface RecipesOfList {

    fun addRecipe(recipe: Recipe)

    fun deleteRecipe(recipe: Recipe)

    fun editRecipe(recipe: Recipe)

    fun getRecipe(recipeId: Int): Recipe

    fun getRecipeList(): LiveData<List<Recipe>>

    fun isFavorite(recipeId: Int)

    fun ingredientsSteps(recipe: Recipe)

    fun cookSteps(recipe: Recipe)

}