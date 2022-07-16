package ru.netology.diplom_work.demo.adapter.listener

import ru.netology.diplom_work.activity.Ingredient

interface IngredientActionListener {

    fun onIngredientUp(ingredient: Ingredient, moveBy: Int)

    fun onIngredientDown(ingredient: Ingredient, moveBy: Int)

    fun onIngredientEdit(ingredient: Ingredient)

    fun onIngredientDelete(ingredient: Ingredient)

}