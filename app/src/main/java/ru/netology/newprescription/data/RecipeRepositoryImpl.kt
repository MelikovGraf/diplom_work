package ru.netology.newprescription.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.newprescription.activity.Recipe
import ru.netology.newprescription.activity.RecipesOfList

object RecipeRepositoryImpl : RecipesOfList { // Список рецептов

    private const val RECIPE_COUNT = 5
    private var id = 0
    private val recipeListData = MutableLiveData<List<Recipe>>()
    private val recipeList = mutableListOf<Recipe>()


    init {
        for (r in 0..RECIPE_COUNT) {
            val newRecipe = Recipe(
                title = "Recipe №$r",
                author = "favourites",
                type = ""
            )
            addRecipe(newRecipe)
        }
    }

    override fun addRecipe(recipe: Recipe) {
        if (recipe.id == Recipe.IDENT) {
            val recipeId = id++
            recipeList.add(recipe.copy(id = recipeId))
        } else editRecipe(recipe)
        updateList()
    }

    override fun deleteRecipe(recipe: Recipe) {
        recipeList.remove(recipe)
        updateList()
    }

    override fun editRecipe(recipe: Recipe) {
        recipeList.replaceAll {
            if (it.id == recipe.id) recipe else it
        }
        updateList()
    }

    override fun getRecipe(recipeId: Int): Recipe {
        return recipeList.find {
            it.id == recipeId
        } ?: throw RuntimeException("The recipe's $recipeId is not in the list!")
    }

    override fun getRecipeList(): LiveData<List<Recipe>> {
        return recipeListData
    }

    private fun updateList() {
        recipeListData.value = recipeList.toList()
    }
}