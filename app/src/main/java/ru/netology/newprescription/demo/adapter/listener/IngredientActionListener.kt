package ru.netology.newprescription.demo.adapter.listener

import ru.netology.newprescription.activity.Ingredient

interface IngredientActionListener {

    fun onIngredientUp(ingredient: Ingredient, moveBy: Int)

    fun onIngredientDown(ingredient: Ingredient, moveBy: Int)

    fun onIngredientEdit(ingredient: Ingredient)

    fun onIngredientDelete(ingredient: Ingredient)

}