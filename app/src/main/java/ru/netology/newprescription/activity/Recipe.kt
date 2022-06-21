package ru.netology.newprescription.activity

data class Recipe(
    val title: String,
    val author: String,
    val type: String,
    val dishTime: String,
    val favorite: Boolean = false,
    val id: Int = IDENT
) {

    companion object {
        const val IDENT = -1
    }
}
