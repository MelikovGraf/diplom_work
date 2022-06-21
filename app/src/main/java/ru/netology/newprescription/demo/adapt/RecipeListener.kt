package ru.netology.newprescription.demo.adapt

import ru.netology.newprescription.activity.Recipe

interface RecipeListener {

    fun onFavoriteClicked(recipe: Recipe)

    fun onRecipeClicked(recipe: Recipe)

}