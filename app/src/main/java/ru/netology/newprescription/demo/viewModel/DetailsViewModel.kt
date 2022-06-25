package ru.netology.newprescription.demo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.netology.newprescription.data.RecipeRepositoryImpl
import ru.netology.newprescription.activity.*
import ru.netology.newprescription.activity.fragment.*
import ru.netology.newprescription.demo.adapt.listener.RecipeDetailsInteractionListener

class DetailsViewModel(

    application: Application
) : AndroidViewModel(application), RecipeDetailsInteractionListener {

    private val repository = RecipeRepositoryImpl

    private val addRecipeItemUseCase = AddRecipeFragment(repository)
    private val deleteRecipeItemUseCase = DeleteRecipeFragment(repository)
    private val editRecipeItemUseCase = EditRecipeFragment(repository)
    private val getRecipeItemUseCase = GetRecipeFragment(repository)
    private val getRecipeListUseCase = GetRecipeListFragment(repository)

    val recipeList = getRecipeListUseCase.getRecipeList()


    fun deleteRecipe(recipe: Recipe) {
        deleteRecipeItemUseCase.deleteRecipe(recipe)
    }

    fun editRecipe(recipe: Recipe) {
        editRecipeItemUseCase.editRecipe(recipe)
    }

    fun addRecipe(recipe: Recipe) {
        addRecipeItemUseCase.addRecipe(recipe)
    }

    fun getRecipe(recipeId: Int): Recipe {
        return getRecipeItemUseCase.getRecipe(recipeId)
    }

    fun getRecipeIngredients(recipe: Recipe): List<Ingredient> {
        return recipe.ingredientsList
    }

    fun getRecipeCookSteps(recipe: Recipe): List<CookingStage> {
        return recipe.cookingList
    }

    override fun onFavoriteClicked(recipe: Recipe) = repository.isFavorite(recipe.id)

    override fun onIngredientsShowClicked(recipe: Recipe) = repository.ingredientsSteps(recipe)

    override fun onCookStepsShowClicked(recipe: Recipe) = repository.cookSteps(recipe)
}