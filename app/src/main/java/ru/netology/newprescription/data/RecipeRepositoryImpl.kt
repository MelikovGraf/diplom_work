package ru.netology.newprescription.data

import ru.netology.newprescription.activity.RecipeItem
import ru.netology.newprescription.activity.RecipesOfList
import java.lang.RuntimeException

object RecipeRepositoryImpl : RecipesOfList { // Список рецептов

    private val recipeList = mutableListOf<RecipeItem>()
    private var id = 0

    override fun addRecipe(recipe: RecipeItem) {
        if (recipe.id == RecipeItem.UNDEFINED_ID) {
            val recipeId = id++
            recipeList.add(recipe.copy(id = recipeId))
        } else recipeList.add(recipe)
    }

    override fun deleteRecipe(recipe: RecipeItem) {
        recipeList.remove(recipe)
    }

    override fun editRecipe(recipe: RecipeItem) {
        val lastRecipe = getRecipe(recipe.id)
        recipeList.remove(lastRecipe)
        addRecipe(recipe)
    }

    override fun getRecipe(recipeId: Int): RecipeItem {
        return recipeList.find {
            it.id == recipeId
        } ?: throw RuntimeException("The recipe's $recipeId is not in the list!")
    }

    override fun getRecipeList(): List<RecipeItem> {

        return recipeList.toList()
    }
}