package ru.netology.diplom_work.demo.adapter.listener

import ru.netology.diplom_work.activity.CookingStage

interface CookingStageActionListener {

    fun onCookingStageUp(cookingStage: CookingStage, moveBy: Int)

    fun onCookingStageDown(cookingStage: CookingStage, moveBy: Int)

    fun onCookingStageEdit(cookingStage: CookingStage)

    fun onCookingStageDelete(cookingStage: CookingStage)

}