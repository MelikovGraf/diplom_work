package ru.netology.newprescription.demo.adapter.listener

import ru.netology.newprescription.activity.CookingStage

interface CookingStageActionListener {

    fun onCookingStageUp(cookingStage: CookingStage, moveBy: Int)

    fun onCookingStageDown(cookingStage: CookingStage, moveBy: Int)

    fun onCookingStageEdit(cookingStage: CookingStage)

    fun onCookingStageDelete(cookingStage: CookingStage)

}