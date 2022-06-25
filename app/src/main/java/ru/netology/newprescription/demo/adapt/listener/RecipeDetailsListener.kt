package ru.netology.newprescription.demo.adapt.listener

import ru.netology.newprescription.activity.Recipe

interface RecipeDetailsInteractionListener {

    fun onFavoriteClicked(recipe: Recipe)

    fun onIngredientsShowClicked(recipe: Recipe)

    fun onCookStepsShowClicked(recipe: Recipe)

}