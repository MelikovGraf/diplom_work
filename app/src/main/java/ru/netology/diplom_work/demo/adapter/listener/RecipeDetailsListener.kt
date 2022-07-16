package ru.netology.diplom_work.demo.adapter.listener

import ru.netology.diplom_work.activity.Recipe

interface RecipeDetailsInteractionListener {

    fun onFavoriteClicked(recipe: Recipe)

    fun onDeleteClicked(recipe: Recipe)

    fun onEditClicked(recipe: Recipe)

}