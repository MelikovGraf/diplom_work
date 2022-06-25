package ru.netology.newprescription.demo.adapt.listener

import ru.netology.newprescription.activity.Recipe

interface RecipeListListener {

    fun onFavoriteClicked(recipe: Recipe)

    fun onRecipeClicked(recipe: Recipe)

    fun onSearchClicked(request: String)

    fun onCancelClicked()

}