package ru.netology.diplom_work.demo.adapter.display

import ru.netology.diplom_work.activity.CookingStage
import java.util.*

typealias  CookingStepListener = (cookingSteps: List<CookingStage>) -> Unit

object CookingStageService {

    private var cookingSteps = mutableListOf<CookingStage>()

    private val listeners = mutableSetOf<CookingStepListener>()

    fun getCookingSteps(): List<CookingStage> {
        return cookingSteps
    }

    fun setCookingStepsList(cookingStepsList: MutableList<CookingStage>) {
        cookingSteps = cookingStepsList
    }

    fun deleteCookingStep(cookingStep: CookingStage) {
        val indexToDelete = cookingSteps.indexOfFirst { it.id == cookingStep.id }
        if (indexToDelete != -1) {
            cookingSteps.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun addCookingStep(cookingStep: CookingStage) {
        if (cookingStep.id == CookingStage.UNDEFINED_ID) {
            if (cookingSteps.isNotEmpty()) {
                val newId = cookingSteps.maxOf { it.id } + 1
                cookingSteps.add(cookingStep.copy(id = newId))
            } else cookingSteps.add(cookingStep.copy(id = 1))
            notifyChanges()
        } else editCookingStep(cookingStep)
    }

    fun editCookingStep(cookingStep: CookingStage) {
        cookingSteps.replaceAll {
            if (it.id == cookingStep.id) cookingStep else it
        }
        notifyChanges()
    }

    fun moveCookingStep(cookingStep: CookingStage, moveBy: Int) {
        val oldIndex = cookingSteps.indexOfFirst { it.id == cookingStep.id }
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= cookingSteps.size) return
        Collections.swap(cookingSteps, oldIndex, newIndex)
        notifyChanges()
    }

    fun addListener(listener: CookingStepListener) {
        listeners.add(listener)
        listener.invoke(cookingSteps)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(cookingSteps) }
    }
}