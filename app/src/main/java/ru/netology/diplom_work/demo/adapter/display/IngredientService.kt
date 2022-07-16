package ru.netology.diplom_work.demo.adapter.display

import ru.netology.diplom_work.activity.Ingredient
import java.util.*

typealias  IngredientListener = (ingredients: List<Ingredient>) -> Unit

object IngredientService {

    private var ingredients = mutableListOf<Ingredient>()

    private val listeners = mutableSetOf<IngredientListener>()

    var targetIngredient: Ingredient? = null

    fun getIngredients(): List<Ingredient> {
        return ingredients
    }

    fun setIngredientsList(ingredientList: MutableList<Ingredient>) {
        ingredients = ingredientList
    }

    fun deleteIngredient(ingredient: Ingredient) {
        val indexToDelete = ingredients.indexOfFirst { it.id == ingredient.id }
        if (indexToDelete != -1) {
            ingredients.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun addIngredient(ingredient: Ingredient) {
        if (ingredient.id == Ingredient.UNDEFINED_ID) {
            if (ingredients.isNotEmpty()) {
                val newId = ingredients.maxOf { it.id } + 1
                ingredients.add(ingredient.copy(id = newId))
            } else ingredients.add(ingredient.copy(id = 1))
            notifyChanges()
        } else editIngredient(ingredient)
    }

    fun editIngredient(ingredient: Ingredient) {
        ingredients.replaceAll {
            if (it.id == ingredient.id) ingredient else it
        }
        notifyChanges()
    }

    fun moveIngredient(ingredient: Ingredient, moveBy: Int) {
        val oldIndex = ingredients.indexOfFirst { it.id == ingredient.id }
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= ingredients.size) return
        Collections.swap(ingredients, oldIndex, newIndex)
        notifyChanges()
    }

    fun addListener(listener: IngredientListener) {
        listeners.add(listener)
        listener.invoke(ingredients)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(ingredients) }
    }
}