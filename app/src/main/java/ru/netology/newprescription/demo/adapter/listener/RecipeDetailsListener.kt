package ru.netology.newprescription.demo.adapter.listener

import ru.netology.newprescription.activity.Recipe

interface RecipeDetailsInteractionListener {

    fun onFavoriteClicked(recipe: Recipe)

    fun onDeleteClicked(recipe: Recipe)

    fun onEditClicked(recipe: Recipe)

}