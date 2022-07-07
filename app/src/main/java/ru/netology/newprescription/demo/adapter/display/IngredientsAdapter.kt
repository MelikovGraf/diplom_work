package ru.netology.newprescription.demo.adapter.display

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.newprescription.R
import ru.netology.newprescription.activity.Ingredient
import ru.netology.newprescription.databinding.IngredientsCompositionBinding
import ru.netology.newprescription.demo.adapter.listener.IngredientActionListener

class IngredientsAdapter(
    private val ingredientActionListener: IngredientActionListener
) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>(), View.OnClickListener {

    var ingredients: List<Ingredient> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ingredient_options_button -> {
                showPopUpMenu(v)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IngredientsCompositionBinding.inflate(inflater, parent, false)

        binding.ingredientOptionsButton.setOnClickListener(this)

        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        with(holder.binding) {
            ingredientOptionsButton.tag = ingredient
            ingredientTitle.text = ingredient.title
            ingredientValue.text = ingredient.value
            ingredientOptionsButton.visibility = View.VISIBLE
        }
    }

    private fun showPopUpMenu(view: View) {
        val context = view.context
        val ingredient = view.tag as Ingredient
        val position = ingredients.indexOfFirst { it.id == ingredient.id }
        PopupMenu(context, view).apply {

            inflate(R.menu.list_element_menu)

            menu.findItem(R.id.shift_up).isEnabled = position > 0

            menu.findItem(R.id.shift_down).isEnabled = position < ingredients.size - 1

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.shift_up -> {
                        ingredientActionListener.onIngredientUp(ingredient, -1)
                        true
                    }
                    R.id.shift_down -> {
                        ingredientActionListener.onIngredientDown(ingredient, 1)
                        true
                    }
                    R.id.edit -> {
                        ingredientActionListener.onIngredientEdit(ingredient)
                        true
                    }
                    R.id.delete -> {
                        ingredientActionListener.onIngredientDelete(ingredient)
                        true
                    }
                    else -> false
                }
            }

        }.show()
    }

    override fun getItemCount(): Int = ingredients.size

    class IngredientViewHolder(
        val binding: IngredientsCompositionBinding
    ) : RecyclerView.ViewHolder(binding.root)
}