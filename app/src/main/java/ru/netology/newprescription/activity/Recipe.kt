package ru.netology.newprescription.activity

data class Recipe(
    val title: String,
    val author: String,
    val type: String,
    val dishTime: String,
    val ingredientsList: List<Ingredient>,
    val cookingList: List<CookingStage>,
    val isIngredients: Boolean = false,
    val isCookingSteps: Boolean = false,
    val favorite: Boolean = false,
    val id: Int = IDENT
) {

    companion object {
        const val IDENT = -1
    }
}
