package ru.netology.newprescription.activity

data class Recipe(
    val id: Int = IDENT,
    val title: String,
    val author: String,
    val type: String
) {

    companion object {
        const val IDENT = -1
    }
}
