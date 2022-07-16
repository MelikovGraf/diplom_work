package ru.netology.diplom_work.demo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.netology.diplom_work.data.RecipeRepositoryImpl
import ru.netology.diplom_work.activity.*
import ru.netology.diplom_work.activity.fragment.*
import ru.netology.diplom_work.demo.adapter.listener.RecipeDetailsInteractionListener
import ru.netology.diplom_work.utils.MultipleDevelopment

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

    override fun onDeleteClicked(recipe: Recipe) {
        deleteRecipe(recipe)
    }

    override fun onEditClicked(recipe: Recipe) {
        navigateToRecipeEditorScreen.value = recipe
    }
}