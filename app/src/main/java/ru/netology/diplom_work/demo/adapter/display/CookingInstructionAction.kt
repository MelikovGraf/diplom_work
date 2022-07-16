package ru.netology.diplom_work.demo.adapter.display

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.diplom_work.R
import ru.netology.diplom_work.activity.CookingStage
import ru.netology.diplom_work.databinding.CreatingDishBinding
import ru.netology.diplom_work.demo.adapter.listener.CookingStageActionListener

class CookingInstructionStepsAdapter(
    val context: Context,
    private val cookingStageActionListener: CookingStageActionListener,
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
            if (cookingStage.stageImageUri == null) {
                stagePreview.visibility = View.GONE
            } else {
                stagePreview.visibility = View.VISIBLE
                Glide.with(holder.binding.stagePreview)
                    .asDrawable()
                    .load(cookingStage.stageImageUri)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(holder.binding.stagePreview)
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
                        cookingStageActionListener.onCookingStageUp(cookingStep, -1)
                        true
                    }
                    R.id.shift_down -> {
                        cookingStageActionListener.onCookingStageDown(cookingStep, 1)
                        true
                    }
                    R.id.edit -> {
                        cookingStageActionListener.onCookingStageEdit(cookingStep)
                        true
                    }
                    R.id.delete -> {
                        cookingStageActionListener.onCookingStageDelete(cookingStep)
                        true
                    }
                    else -> false
                }
            }

        }.show()
    }

    override fun getItemCount(): Int = cookingStages.size

    class CookingStepViewHolder(
        val binding: CreatingDishBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}