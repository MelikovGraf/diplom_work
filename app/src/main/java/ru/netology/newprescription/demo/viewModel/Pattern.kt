package ru.netology.newprescription.demo.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import ru.netology.newprescription.activity.*
import ru.netology.newprescription.data.RecipeRepositoryImpl
import ru.netology.newprescription.demo.adapt.RecipeListener

class Pattern(

    application: Application
) : AndroidViewModel(application), RecipeListener {

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

    override fun onFavoriteClicked(recipe: Recipe) = repository.isFavorite(recipe.id)

    override fun onRecipeClicked(recipe: Recipe) {
        Toast.makeText(
            getApplication<Application>().applicationContext,
            "The author of the recipe ${recipe.author}",
            Toast.LENGTH_LONG
        ).show()
    }
}