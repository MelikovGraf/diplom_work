package ru.netology.diplom_work.demo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.netology.diplom_work.activity.*
import ru.netology.diplom_work.activity.fragment.*
import ru.netology.diplom_work.activity.repository.RecipesOfList
import ru.netology.diplom_work.data.RecipeRepositoryImpl
import ru.netology.diplom_work.demo.adapter.listener.RecipeListListener
import ru.netology.diplom_work.utils.MultipleDevelopment

class ListViewModel(

    application: Application
) : AndroidViewModel(application), RecipeListListener {

    private val repository = RecipeRepositoryImpl

    private val addRecipeFragment = AddRecipeFragment(repository)
    private val getRecipeListFragment = GetRecipeListFragment(repository)

    val recipeList = getRecipeListFragment.getRecipeList()

    val navigateToRecipeDetailsScreen = MultipleDevelopment<Recipe>()

    val navigateToRecipeEditorScreen = MultipleDevelopment<Recipe?>()

    fun addRecipe(recipe: Recipe) {
        addRecipeFragment.addRecipe(recipe)
    }

    override fun onFavoriteClicked(recipe: Recipe) = repository.isFavorite(recipe.id)

    override fun onRecipeClicked(recipe: Recipe) {
        navigateToRecipeDetailsScreen.value = recipe
    }

    override fun onSearchClicked(request: String) = repository.searchRecipe(request)

    override fun onAddClicked() {
        navigateToRecipeEditorScreen.value = null
    }

    override fun onCancelClicked() = repository.searchRecipe(RecipesOfList.CANCEL_SEARCH)
}