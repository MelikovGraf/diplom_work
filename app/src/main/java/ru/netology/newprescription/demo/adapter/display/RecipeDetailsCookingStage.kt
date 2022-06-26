package ru.netology.newprescription.demo.adapter.display

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.newprescription.databinding.CreatingDishBinding
import ru.netology.newprescription.activity.CookingStage

class RecipeDetailsCookingStage :
    ListAdapter<CookingStage, RecipeDetailsCookingStage.CookingStepsViewHolder>(ReverseCookingStage) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookingStepsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CreatingDishBinding.inflate(inflater, parent, false)
        return CookingStepsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CookingStepsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CookingStepsViewHolder(
        private val binding: CreatingDishBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var cookingStage: CookingStage

        fun bind(cookingStage: CookingStage) {
            this.cookingStage = cookingStage
            with(binding) {
                cookingStageDescription.text = cookingStage.descript
                stage.text = "Stage ${adapterPosition + 1}"
                if (cookingStage.uri == null) {
                    stagePreview.visibility = View.GONE
                }
                cookingStageOptions.visibility = View.GONE
            }
        }
    }
}

private object ReverseCookingStage : DiffUtil.ItemCallback<CookingStage>() {
    override fun areItemsTheSame(oldItem: CookingStage, newItem: CookingStage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CookingStage, newItem: CookingStage): Boolean {
        return oldItem == newItem
    }
}