package ru.netology.newprescription.activity.fragment

import ru.netology.newprescription.activity.Recipe
import ru.netology.newprescription.activity.repository.RecipesOfList

class EditRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun editRecipe(recipe: Recipe) {
        listOfRecipes.editRecipe(recipe)
    }
}