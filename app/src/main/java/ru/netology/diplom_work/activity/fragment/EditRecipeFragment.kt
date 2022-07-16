package ru.netology.diplom_work.activity.fragment

import ru.netology.diplom_work.activity.Recipe
import ru.netology.diplom_work.activity.repository.RecipesOfList

class EditRecipeFragment(private val listOfRecipes: RecipesOfList) {

    fun editRecipe(recipe: Recipe) {
        listOfRecipes.editRecipe(recipe)
    }
}