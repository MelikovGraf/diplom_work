package ru.netology.newprescription.activity.fragment

import ru.netology.newprescription.activity.Recipe
import ru.netology.newprescription.activity.repository.RecipesOfList

class AddRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun addRecipe(recipe: Recipe) {
        listOfRecipes.addRecipe(recipe)
    }
}