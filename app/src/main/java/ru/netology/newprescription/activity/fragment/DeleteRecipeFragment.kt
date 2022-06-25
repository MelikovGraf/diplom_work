package ru.netology.newprescription.activity.fragment

import ru.netology.newprescription.activity.Recipe
import ru.netology.newprescription.activity.repository.RecipesOfList

class DeleteRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun deleteRecipe(recipe: Recipe) {
        listOfRecipes.deleteRecipe(recipe)
    }
}