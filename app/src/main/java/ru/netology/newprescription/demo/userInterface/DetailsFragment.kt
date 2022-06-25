package ru.netology.newprescription.demo.userInterface

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.newprescription.R
import ru.netology.newprescription.databinding.RecipeFunctionsFragmentBinding
import ru.netology.newprescription.demo.adapt.RecipeCookingStage
import ru.netology.newprescription.demo.adapt.RecipeIngredients
import ru.netology.newprescription.demo.viewModel.DetailsViewModel

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeFunctionsFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val toolBarEditText = activity?.findViewById(R.id.toolBarEditText) as EditText

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (toolBarEditText.text.isNotEmpty()) toolBarEditText.visibility = View.VISIBLE
            findNavController().popBackStack()
        }

        viewModel.recipeList.observe(viewLifecycleOwner) { recipeList ->
            val recipe = recipeList.find { it.id == args.recipeId } ?: return@observe
            with(binding) {
                recipeItems.author.text = recipe.author
                recipeItems.title.text = recipe.title
                recipeItems.category.text = recipe.type
                recipeItems.dishTime.text = recipe.dishTime
                recipeItems.favoriteButton.setImageResource(
                    when (recipe.favorite) {
                        true -> R.drawable.ic_baseline_favorite_24
                        false -> R.drawable.ic_baseline_favorite_border_24
                    }
                )

                val ingredientsAdapter = RecipeIngredients()
                binding.ingredientView.adapter = ingredientsAdapter
                ingredientsAdapter.submitList(recipe.ingredientsList)

                val cookingStepsAdapter = RecipeCookingStage()
                binding.cookingCompositionView.adapter = cookingStepsAdapter
                cookingStepsAdapter.submitList(recipe.cookingList)

                when (recipe.isIngredients) {
                    true -> {
                        ingredientView.visibility = View.VISIBLE
                        ingredientsButton.setIconResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    }
                    false -> {
                        ingredientView.visibility = View.GONE
                        ingredientsButton.setIconResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    }
                }

                when (recipe.isCookingSteps) {
                    true -> {
                        cookingCompositionView.visibility = View.VISIBLE
                        cookingCompositionButton.setIconResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    }
                    false -> {
                        cookingCompositionView.visibility = View.GONE
                        cookingCompositionButton.setIconResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    }
                }

                cookingCompositionButton.setOnClickListener {
                    viewModel.onCookStepsShowClicked(recipe)
                }

                ingredientsButton.setOnClickListener {
                    viewModel.onIngredientsShowClicked(recipe)
                }

                recipeItems.favoriteButton.setOnClickListener {
                    viewModel.onFavoriteClicked(recipe)
                }

            }
        }
    }.root

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_bar_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val toolBarEditText = activity?.findViewById(R.id.toolBarEditText) as EditText
        toolBarEditText.visibility = View.GONE
        super.onPrepareOptionsMenu(menu)
        with(menu) {
            findItem(R.id.add_button).isVisible = false
            findItem(R.id.edit_button).isVisible = true
            findItem(R.id.cancel_button).isVisible = false
            findItem(R.id.delete_button).isVisible = true
            findItem(R.id.search_button).isVisible = false
            findItem(R.id.filter_button).isVisible = false
        }
    }
}