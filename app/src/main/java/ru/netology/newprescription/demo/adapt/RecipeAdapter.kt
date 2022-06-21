package ru.netology.newprescription.demo.adapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.newprescription.R
import ru.netology.newprescription.databinding.RecipeViewFragmentBinding
import ru.netology.newprescription.activity.Recipe

class RecipeAdapter(

    private val recipeListener: RecipeListener
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeViewFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, recipeListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: RecipeViewFragmentBinding,
        private val listener: RecipeListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        init {
            itemView.setOnClickListener {
                listener.onRecipeClicked(recipe)
            }
            binding.favoriteButton.setOnClickListener { listener.onFavoriteClicked(recipe) }

        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                author.text = recipe.author
                title.text = recipe.title
                category.text = recipe.type
                dishTime.text = recipe.dishTime
                recipeOverview.setImageResource(R.mipmap.ic_dish)
                when (recipe.favorite) {
                    true -> favoriteButton.setImageResource(R.drawable.ic_baseline_star_24)
                    false -> favoriteButton.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
            }
        }

    }
}

private object DiffCallBack : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }

}