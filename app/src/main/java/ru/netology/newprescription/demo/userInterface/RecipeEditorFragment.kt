package ru.netology.newprescription.demo.userInterface

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.newprescription.R
import ru.netology.newprescription.activity.CookingStage
import ru.netology.newprescription.databinding.RecipeEditorFragmentBinding
import ru.netology.newprescription.activity.Ingredient
import ru.netology.newprescription.activity.Recipe
import ru.netology.newprescription.demo.adapter.display.*
import ru.netology.newprescription.utils.CookingTimeConverter


class RecipeEditorFragment : Fragment() {

    private val args by navArgs<RecipeEditorFragmentArgs>()  // recipe or null

    private val ingredientService: IngredientService = IngredientService
    private val cookingStepsService: CookingStageService = CookingStageService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeEditorFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val recipe = args.recipe

        val ingredientsAdapter = IngredientsAdapter(object : IngredientActionListener {
            override fun onIngredientUp(ingredient: Ingredient, moveBy: Int) {
                ingredientService.moveIngredient(ingredient, moveBy)
            }

            override fun onIngredientDown(ingredient: Ingredient, moveBy: Int) {
                ingredientService.moveIngredient(ingredient, moveBy)
            }

            override fun onIngredientEdit(ingredient: Ingredient) {
                ingredientService.targetIngredient = ingredient
                binding.ingredientEditGroup.visibility = View.VISIBLE
                binding.addIngredientButton.text = getString(R.string.save_ingredient)
                binding.newIngredientNameEditText.setText(ingredient.title)
                binding.newIngredientValueEditText.setText(ingredient.value)
            }

            override fun onIngredientDelete(ingredient: Ingredient) {
                ingredientService.deleteIngredient(ingredient)
            }
        })

        val cookingInstructionStepsAdapter =
            this.context?.let {
                CookingInstructionStepsAdapter(it, object : CookingInstructionAction {
                    override fun onCookingStageUp(cookingStage: CookingStage, moveBy: Int) {
                        cookingStepsService.moveCookingStep(cookingStage, moveBy)
                    }

                    override fun onCookingStageDown(cookingStage: CookingStage, moveBy: Int) {
                        cookingStepsService.moveCookingStep(cookingStage, moveBy)
                    }

                    override fun onCookingStageEdit(cookingStage: CookingStage) {
                        val direction =
                            RecipeEditorFragmentDirections.toCookingStageEditorFragment(cookingStage)
                        findNavController().navigate(direction)
                    }

                    override fun onCookingStageDelete(cookingStage: CookingStage) {
                        cookingStepsService.deleteCookingStep(cookingStage)
                    }
                })
            }

        if (recipe !== null) {
            ingredientService.setIngredientsList(recipe.ingredientsList)
            cookingStepsService.setCookingStepsList(recipe.cookingList)
            binding.title.setText(recipe.title)
            binding.cookingTimeHours.setText(CookingTimeConverter.convertToHours(recipe.dishTime))
            binding.cookingTimeMinutes.setText(CookingTimeConverter.convertToMinutes(recipe.dishTime))
            binding.kitchenCategoryTitle.text = recipe.cuisineCategory
        }

        binding.ingredientEditGroup.visibility = View.GONE

        binding.ingredientsList.adapter = ingredientsAdapter
        binding.cookingInstructionList.adapter = cookingInstructionStepsAdapter

        val ingredientsListener: IngredientListener = {
            ingredientsAdapter.ingredients = ingredientService.getIngredients()
        }
        val cookingStepsListener: CookingStepListener = {
            cookingInstructionStepsAdapter?.cookingStages = cookingStepsService.getCookingSteps()
        }

        ingredientService.addListener(ingredientsListener)  //TODO
        cookingStepsService.addListener(cookingStepsListener)  //TODO

        binding.cancelEditIngredientButton.setOnClickListener {
            binding.newIngredientNameEditText.text.clear()
            binding.newIngredientValueEditText.text.clear()
            binding.addIngredientButton.text = getString(R.string.add_ingredient)
            binding.ingredientEditGroup.visibility = View.GONE
        }
        binding.kitchenCategoryButton.setOnClickListener {
            PopupMenu(binding.root.context, binding.kitchenCategoryButton).apply {
                inflate(R.menu.dish_category_menu)
                setOnMenuItemClickListener {
                    binding.kitchenCategoryTitle.text = it.title
                    true
                }
            }.show()
        }
        binding.addIngredientButton.setOnClickListener {
            val ingredientEditGroupVisibility = binding.ingredientEditGroup.visibility
            val targetIngredient =
                ingredientService.targetIngredient // notnull means ingredient edit mode

            // too much if coz of hard logic
            if (ingredientEditGroupVisibility == View.GONE) {
                binding.ingredientEditGroup.visibility = View.VISIBLE
                binding.addIngredientButton.text = getString(R.string.save_ingredient)
            }
            if (ingredientEditGroupVisibility == View.VISIBLE && binding.newIngredientNameEditText.text.isNullOrBlank()) {
                toastMaker("Ingredient name field is empty")
                return@setOnClickListener
            }
            if (ingredientEditGroupVisibility == View.VISIBLE && targetIngredient !== null) {
                ingredientService.targetIngredient = targetIngredient.copy(
                    title = binding.newIngredientNameEditText.text.toString(),
                    value = binding.newIngredientValueEditText.text.toString()
                )
                ingredientService.targetIngredient?.let { ingredient ->
                    ingredientService.editIngredient(ingredient)
                }
                ingredientService.targetIngredient = null
                binding.newIngredientNameEditText.text.clear()
                binding.newIngredientValueEditText.text.clear()
                binding.ingredientEditGroup.visibility = View.GONE
                binding.addIngredientButton.text = getString(R.string.add_ingredient)
                return@setOnClickListener
            }
            if (ingredientEditGroupVisibility == View.VISIBLE && targetIngredient == null) {
                ingredientService.targetIngredient = Ingredient(
                    title = binding.newIngredientNameEditText.text.toString(),
                    value = binding.newIngredientValueEditText.text.toString()
                )
                ingredientService.targetIngredient?.let { ingredient ->
                    ingredientService.addIngredient(ingredient)
                }
                ingredientService.targetIngredient = null
                binding.newIngredientNameEditText.text.clear()
                binding.newIngredientValueEditText.text.clear()
                binding.ingredientEditGroup.visibility = View.GONE
                binding.addIngredientButton.text = getString(R.string.add_ingredient)
                return@setOnClickListener
            }

        }
        binding.addCookingStepButton.setOnClickListener {
            val direction = RecipeEditorFragmentDirections.toCookingStageEditorFragment(null)
            findNavController().navigate(direction)
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
            findItem(R.id.search_button).isVisible = false
            findItem(R.id.add_button).isVisible = false
            findItem(R.id.filter_button).isVisible = false
            findItem(R.id.edit_button).isVisible = false
            findItem(R.id.delete_button).isVisible = false
            findItem(R.id.ok_button).isVisible = true
            findItem(R.id.cancel_button).isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ok_button -> {
                val resultBundle = Bundle(1)
                val newRecipe = Recipe(
                    id = if (args.recipe !== null) args.recipe!!.id else -1,
                    title = view?.findViewById<EditText>(R.id.title)?.text.toString(),
                    author = "Me", //TODO account name
                    authorId = 2, //TODO account id
                    cuisineCategory = view?.findViewById<TextView>(R.id.kitchen_category_title)?.text.toString(),
                    dishTime = CookingTimeConverter.convertToString(
                        view?.findViewById<EditText>(R.id.cooking_time_hours)?.text.toString(),
                        view?.findViewById<EditText>(R.id.cooking_time_minutes)?.text.toString()
                    ),
                    ingredientsList = IngredientService.getIngredients(),
                    cookingList = CookingStageService.getCookingSteps(),
                    isIngredients = false,
                    isCookingSteps = false,
                    favorite = false

                )
                if (checkRecipeForEmptyFields(newRecipe)) {
                    resultBundle.putParcelable(RESULT_KEY_NEW_STAGE, newRecipe)
                    setFragmentResult(ORDER_KEY, resultBundle)
                    findNavController().popBackStack()
                }
                true
            }
            R.id.cancel_button -> {
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkRecipeForEmptyFields(recipe: Recipe): Boolean {
        return when {
            recipe.title.isBlank() -> {
                toastMaker("title shouldn't be empty")
                false
            }
            recipe.ingredientsList.isEmpty() -> {
                toastMaker("Please add at least one ingredient")
                false
            }
            recipe.cookingList.isEmpty() -> {
                toastMaker("Please add at least one cooking instruction step")
                false
            }
            else -> true
        }
    }

    private fun toastMaker(value: String) {
        Toast.makeText(
            context,
            value,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val RESULT_KEY_NEW_STAGE = "add new recipe"
        const val ORDER_KEY = "requestKey"
    }
}