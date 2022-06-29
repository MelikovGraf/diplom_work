package ru.netology.newprescription.demo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.netology.newprescription.data.RecipeRepositoryImpl
import ru.netology.newprescription.activity.*
import ru.netology.newprescription.activity.fragment.*
import ru.netology.newprescription.demo.adapter.listener.RecipeDetailsInteractionListener
import ru.netology.newprescription.utils.MultipleDevelopment

class DetailsViewModel(

    application: Application
) : AndroidViewModel(application), RecipeDetailsInteractionListener {

    private val repository = RecipeRepositoryImpl

    private val deleteRecipeItemUseCase = DeleteRecipeFragment(repository)
    private val editRecipeItemUseCase = EditRecipeFragment(repository)
    private val getRecipeListUseCase = GetRecipeListFragment(repository)

    val recipeList = getRecipeListUseCase.getRecipeList()

    val navigateToRecipeEditorScreen = MultipleDevelopment<Recipe?>()

    fun deleteRecipe(recipe: Recipe) {
        deleteRecipeItemUseCase.deleteRecipe(recipe)
    }

    fun editRecipe(recipe: Recipe) {
        editRecipeItemUseCase.editRecipe(recipe)
    }

    override fun onFavoriteClicked(recipe: Recipe) = repository.isFavorite(recipe.id)

    override fun onIngredientsShowClicked(recipe: Recipe) = repository.ingredientsSteps(recipe)

    override fun onCookStepsShowClicked(recipe: Recipe) = repository.cookSteps(recipe)

    override fun onDeleteClicked(recipe: Recipe) {
        deleteRecipe(recipe)
    }

    override fun onEditClicked(recipe: Recipe) {
        navigateToRecipeEditorScreen.value = recipe
    }
}