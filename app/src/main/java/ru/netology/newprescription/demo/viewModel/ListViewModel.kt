package ru.netology.newprescription.demo.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import ru.netology.newprescription.activity.*
import ru.netology.newprescription.activity.fragment.AddRecipeFragment
import ru.netology.newprescription.activity.fragment.DeleteRecipeFragment
import ru.netology.newprescription.activity.fragment.EditRecipeFragment
import ru.netology.newprescription.activity.fragment.GetRecipeFragment
import ru.netology.newprescription.activity.repository.RecipesOfList
import ru.netology.newprescription.data.RecipeRepositoryImpl
import ru.netology.newprescription.demo.adapt.listener.RecipeListListener
import ru.netology.newprescription.utils.MultipleDevelopment

class ListViewModel(

    application: Application
) : AndroidViewModel(application), RecipeListListener {

    private val repository = RecipeRepositoryImpl

    private val addRecipeFragment = AddRecipeFragment(repository)
    private val deleteRecipeFragment = DeleteRecipeFragment(repository)
    private val editRecipeFragment = EditRecipeFragment(repository)
    private val getRecipeFragment = GetRecipeFragment(repository)
    private val getRecipeListFragment = GetRecipeListFragment(repository)

    val recipeList = getRecipeListFragment.getRecipeList()

    val navigateToRecipeDetailsScreen = MultipleDevelopment<Recipe>()

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
            "The author of the recipe ${recipe.id}",
            Toast.LENGTH_SHORT
        ).show()
        navigateToRecipeDetailsScreen.value = recipe
    }

    override fun onSearchClicked(request: String) = repository.searchRecipe(request)

    override fun onCancelClicked() = repository.searchRecipe(RecipesOfList.CANCEL_SEARCH)
}