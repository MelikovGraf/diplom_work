package ru.netology.newprescription.demo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.netology.newprescription.activity.*
import ru.netology.newprescription.data.RecipeRepositoryImpl

class Pattern(

    application: Application
) : AndroidViewModel(application) {

    private val repository = RecipeRepositoryImpl

    private val addRecipeFragment = AddRecipeFragment(repository)
    private val deleteRecipeFragment = DeleteRecipeFragment(repository)
    private val editRecipeFragment = EditRecipeFragment(repository)
    private val getRecipeFragment = GetRecipeFragment(repository)
    private val getRecipeListFragment = GetRecipeListFragment(repository)

    val recipeList = getRecipeListFragment.getRecipeList()

    fun deleteRecipe(recipe: Recipe) {
        deleteRecipeFragment.deleteRecipe(recipe)
    }

    fun editRecipe(recipe: Recipe) {
        editRecipeFragment.editRecipe(recipe)
    }

    fun addRecipe(recipe: Recipe) {
        addRecipeFragment.addRecipe(recipe)
    }

    fun getRecipe(recipeId: Int): Recipe {
        return getRecipeFragment.getRecipe(recipeId)
    }

}