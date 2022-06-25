package ru.netology.newprescription.activity.fragment

import ru.netology.newprescription.activity.Recipe
import ru.netology.newprescription.activity.repository.RecipesOfList

class GetRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun getRecipe(recipeId: Int): Recipe {
        return listOfRecipes.getRecipe(recipeId)
    }
}