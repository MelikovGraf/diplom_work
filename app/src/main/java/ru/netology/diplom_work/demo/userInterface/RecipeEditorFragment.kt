package ru.netology.diplom_work.demo.userInterface

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.netology.diplom_work.R
import ru.netology.diplom_work.activity.CookingStage
import ru.netology.diplom_work.databinding.RecipeEditorFragmentBinding
import ru.netology.diplom_work.activity.Ingredient
import ru.netology.diplom_work.activity.Recipe
import ru.netology.diplom_work.demo.adapter.display.*
import ru.netology.diplom_work.demo.adapter.listener.CookingStageActionListener
import ru.netology.diplom_work.demo.adapter.listener.IngredientActionListener
import ru.netology.diplom_work.utils.CookingTimeConverter


class RecipeEditorFragment : Fragment() {

    private val args by navArgs<RecipeEditorFragmentArgs>()

    private val ingredientService: IngredientService = IngredientService
    private val cookingStepsService: CookingStageService = CookingStageService

    private var selectedRecipePreviewImageUri: Uri? = null

    private val selectImages: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            selectedRecipePreviewImageUri = imageUri
            imageRecipePreviewUri = imageUri
            imageRecipePreviewTag = null
            view?.findViewById<ImageView>(R.id.recipe_preview)?.setImageURI(imageUri)
            view?.findViewById<ImageButton>(R.id.add_preview_button)?.visibility = View.GONE
            view?.findViewById<ImageButton>(R.id.clear_preview_button)?.visibility = View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeEditorFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            clearCache()
            imageRecipePreviewUri = null
            imageRecipePreviewTag = null
            findNavController().popBackStack()
        }

        val recipe = args.recipe

        if (recipe !== null) {
            ingredientService.setIngredientsList(recipe.ingredientsList as MutableList<Ingredient>)
            cookingStepsService.setCookingStepsList(recipe.cookingList as MutableList<CookingStage>)
            binding.title.setText(recipe.title)
            binding.cookingTimeHours.setText(CookingTimeConverter.convertToHours(recipe.dishTime))
            binding.cookingTimeMinutes.setText(CookingTimeConverter.convertToMinutes(recipe.dishTime))
            binding.kitchenCategoryTitle.text = recipe.cuisineCategory
            if (recipe.previewUri !== null) {
                imageRecipePreviewTag = null
                binding.addPreviewButton.visibility = View.GONE
                binding.clearPreviewButton.visibility = View.VISIBLE
                Glide.with(this)
                    .asDrawable()
                    .load(recipe.previewUri)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(binding.recipePreview)
            } else {
                binding.recipePreview.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
                binding.addPreviewButton.visibility = View.VISIBLE
                binding.clearPreviewButton.visibility = View.GONE
            }
        }

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
                CookingInstructionStepsAdapter(it, object : CookingStageActionListener {
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

        binding.clearPreviewButton.setOnClickListener {
            binding.addPreviewButton.visibility = View.VISIBLE
            binding.clearPreviewButton.visibility = View.GONE
            binding.recipePreview.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
            imageRecipePreviewUri = null
            imageRecipePreviewTag = imageRecipePreviewIsEmptyTag
        }

        binding.addPreviewButton.setOnClickListener {
            selectImages.launch(RESULT_IMAGES)
        }

        binding.cancelEditIngredientButton.setOnClickListener {
            binding.newIngredientNameEditText.text.clear()
            binding.newIngredientValueEditText.text.clear()
            binding.addIngredientButton.text = getString(R.string.add_ingredient)
            binding.ingredientEditGroup.visibility = View.GONE
            imageRecipePreviewUri = null
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
                ingredientService.targetIngredient

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
            findItem(R.id.clear_button).isVisible = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val binding = RecipeEditorFragmentBinding.inflate(layoutInflater)
        return when (item.itemId) {
            R.id.ok_button -> {
                val resultBundle = Bundle(1)
                val newRecipe = Recipe(
                    id = if (args.recipe !== null) args.recipe!!.id else -1,
                    title = view?.findViewById<EditText>(R.id.title)?.text.toString(),
                    author = "Unknown",
                    authorId = 2,
                    cuisineCategory = view?.findViewById<TextView>(R.id.kitchen_category_title)?.text.toString(),
                    dishTime = CookingTimeConverter.convertToString(
                        view?.findViewById<EditText>(R.id.cooking_time_hours)?.text.toString(),
                        view?.findViewById<EditText>(R.id.cooking_time_minutes)?.text.toString()
                    ),
                    ingredientsList = IngredientService.getIngredients(),
                    cookingList = CookingStageService.getCookingSteps(),
                    previewUri = when {
                        imageRecipePreviewUri !== null -> imageRecipePreviewUri
                        imageRecipePreviewTag == imageRecipePreviewIsEmptyTag -> null
                        args.recipe?.previewUri !== null -> args.recipe?.previewUri
                        else -> null
                    },
                    isIngredients = false,
                    isCookingSteps = false,
                    favorite = false

                )
                Log.d("TAG", "R.drawable.ic_baseline_image_not_supported_24 id ${R.drawable.ic_baseline_image_not_supported_24}")
                if (checkRecipeForEmptyFields(newRecipe)) {
                    clearCache()
                    resultBundle.putParcelable(RESULT_KEY_NEW_STAGE, newRecipe)
                    setFragmentResult(ORDER_KEY, resultBundle)
                    findNavController().popBackStack()
                }
                true
            }
            R.id.clear_button -> {
                clearCache()
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

    private fun clearCache() {
        ingredientService.targetIngredient = null
        ingredientService.setIngredientsList(mutableListOf())
        cookingStepsService.setCookingStepsList(mutableListOf())
    }

    companion object {
        const val RESULT_KEY_NEW_STAGE = "add new recipe"
        const val ORDER_KEY = "requestKey"
        const val RESULT_IMAGES = "image/*"
        var imageRecipePreviewUri: Uri? = null
        var imageRecipePreviewTag : String? = null
        const val imageRecipePreviewIsEmptyTag = "empty image preview"
    }
}