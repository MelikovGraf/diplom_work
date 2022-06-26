package ru.netology.newprescription.demo.adapter.display

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.newprescription.R
import ru.netology.newprescription.databinding.CreatingDishBinding
import ru.netology.newprescription.activity.CookingStage

interface CookingInstructionAction {

    fun onCookingStageUp(cookingStage: CookingStage, moveBy: Int)

    fun onCookingStageDown(cookingStage: CookingStage, moveBy: Int)

    fun onCookingStageEdit(cookingStage: CookingStage)

    fun onCookingStageDelete(cookingStage: CookingStage)

}

class CookingInstructionStepsAdapter(
    val context: Context,
    private val cookingInstructionAction: CookingInstructionAction
) : RecyclerView.Adapter<CookingInstructionStepsAdapter.CookingStepViewHolder>(),
    View.OnClickListener {


    var cookingStages: List<CookingStage> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cooking_stage_options -> {
                popUpMenu(v)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookingStepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CreatingDishBinding.inflate(inflater, parent, false)

        binding.cookingStageOptions.setOnClickListener(this)

        return CookingStepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CookingStepViewHolder, position: Int) {
        val cookingStage = cookingStages[position]
        with(holder.binding) {
            cookingStageOptions.tag = cookingStage
            stage.text = context.getString(R.string.stage, position + 1)
            cookingStageDescription.text = cookingStage.descript
            if (cookingStage.uri == null) {
                stagePreview.visibility = View.GONE
            }
            cookingStageOptions.visibility = View.VISIBLE
        }
    }

    private fun popUpMenu(view: View) {
        val context = view.context
        val cookingStep = view.tag as CookingStage
        val position = cookingStages.indexOfFirst { it.id == cookingStep.id }
        PopupMenu(context, view).apply {

            inflate(R.menu.list_element_menu)

            menu.findItem(R.id.shift_up).isEnabled = position > 0

            menu.findItem(R.id.shift_down).isEnabled = position < cookingStages.size - 1

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.shift_up -> {
                        cookingInstructionAction.onCookingStageUp(cookingStep, -1)
                        true
                    }
                    R.id.shift_down -> {
                        cookingInstructionAction.onCookingStageDown(cookingStep, 1)
                        true
                    }
                    R.id.edit -> {
                        cookingInstructionAction.onCookingStageEdit(cookingStep)
                        true
                    }
                    R.id.delete -> {
                        cookingInstructionAction.onCookingStageDelete(cookingStep)
                        true
                    }
                    else -> false
                }
            }

        }.show()
    }

    override fun getItemCount(): Int = cookingStages.size

    class CookingStepViewHolder(
        val binding: CreatingDishBinding
    ) : RecyclerView.ViewHolder(binding.root)
}