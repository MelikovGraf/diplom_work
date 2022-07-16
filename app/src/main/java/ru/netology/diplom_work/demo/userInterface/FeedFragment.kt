package ru.netology.diplom_work.demo.userInterface

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.netology.diplom_work.R
import ru.netology.diplom_work.activity.Cuisine
import ru.netology.diplom_work.activity.Recipe
import ru.netology.diplom_work.databinding.FeedFragmentBinding
import ru.netology.diplom_work.demo.adapter.RecipeAdapter
import ru.netology.diplom_work.demo.viewModel.ListViewModel

class FeedFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel.navigateToRecipeDetailsScreen.observe(this) { recipe ->
            val direction = FeedFragmentDirections.toDetailsFragment(recipe.id)
            findNavController().navigate(direction)
        }

        viewModel.navigateToRecipeEditorScreen.observe(this) { recipe ->
            val direction = FeedFragmentDirections.toRecipeEditorFragment(recipe)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        setFragmentResultListener(requestKey = RecipeEditorFragment.ORDER_KEY) { requestKey, bundle ->
            if (requestKey !== RecipeEditorFragment.ORDER_KEY) return@setFragmentResultListener
            val newRecipe =
                bundle.getParcelable<Recipe>(RecipeEditorFragment.RESULT_KEY_NEW_STAGE)
            if (newRecipe != null)
                viewModel.addRecipe(newRecipe)
        }

        val adapter = RecipeAdapter(viewModel)
        val selectedKitchenList = OptionsFragment.initSettings()

        binding.recipeRecyclerView.adapter = adapter

        viewModel.recipeList.observe(viewLifecycleOwner) { recipeList ->
            val myId = 2
            val filteredCategory = Cuisine.selectedKitchenList.filter { it.isChecked }.map { it.title }

            setNoItemImageVisibility(binding, recipeList)

            when (binding.bottomNavigation.selectedItemId) {
                R.id.all_recipes -> {
                    val recipes = recipeList.filter { it.cuisineCategory in filteredCategory }
                    adapter.submitList(recipes)
                    setNoItemImageVisibility(binding, recipes)
                }
                R.id.my_recipes -> {
                    val recipes = recipeList.filter { it.authorId == myId && it.cuisineCategory in filteredCategory }
                    adapter.submitList(recipes)
                    setNoItemImageVisibility(binding, recipes)
                }
                R.id.favorite_recipes -> {
                    val recipes = recipeList.filter { it.favorite && it.cuisineCategory in filteredCategory }
                    adapter.submitList(recipes)
                    setNoItemImageVisibility(binding, recipes)
                }
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val filteredCategory = Cuisine.selectedKitchenList.filter { it.isChecked }.map { it.title }
            val recipeList = viewModel.recipeList.value
            when (item.itemId) {
                R.id.all_recipes -> {
                    requireActivity().invalidateOptionsMenu()
                    val recipes = recipeList?.filter { it.cuisineCategory in filteredCategory }
                    adapter.submitList(recipes)
                    setNoItemImageVisibility(binding, recipes)
                    return@setOnItemSelectedListener true
                }
                R.id.my_recipes -> {
                    requireActivity().invalidateOptionsMenu()
                    val myId = 2
                    val recipes = recipeList?.filter { it.authorId == myId && it.cuisineCategory in filteredCategory }
                    adapter.submitList(recipes)
                    setNoItemImageVisibility(binding, recipes)
                    return@setOnItemSelectedListener true
                }
                R.id.favorite_recipes -> {
                    requireActivity().invalidateOptionsMenu()
                    val recipes = recipeList?.filter { it.favorite && it.cuisineCategory in filteredCategory }
                    adapter.submitList(recipes)
                    setNoItemImageVisibility(binding, recipes)
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }.root

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_bar_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val toolBarEditText = activity?.findViewById(R.id.toolBarEditText) as EditText
        val bottomNavigationView =
            activity?.findViewById(R.id.bottomNavigation) as BottomNavigationView
        with(menu) {
            findItem(R.id.edit_button).isVisible = false
            findItem(R.id.delete_button).isVisible = false
            findItem(R.id.ok_button).isVisible = false
            when (bottomNavigationView.selectedItemId) {
                R.id.my_recipes -> {
                    findItem(R.id.add_button).isVisible = true
                }
                R.id.all_recipes, R.id.favorite_recipes -> {
                    findItem(R.id.add_button).isVisible = false
                }
            }
            when (toolBarEditText.visibility) {
                View.VISIBLE -> {
                    findItem(R.id.filter_button).isVisible = false
                    findItem(R.id.clear_button).isVisible = true
                }
                View.GONE, View.INVISIBLE -> {
                    findItem(R.id.filter_button).isVisible = true
                    findItem(R.id.clear_button).isVisible = false
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val toolBarEditText = activity?.findViewById(R.id.toolBarEditText) as EditText
        return when (item.itemId) {
            R.id.search_button -> {
                if (toolBarEditText.visibility == View.GONE) {
                    toolBarEditText.visibility = View.VISIBLE
                    requireActivity().invalidateOptionsMenu()
                } else {
                    if (toolBarEditText.text.isNullOrBlank()) {
                        Toast.makeText(
                            this.context,
                            "Searching field is empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.onSearchClicked(toolBarEditText.text.toString())
                    }
                }
                true
            }

            R.id.clear_button -> {
                toolBarEditText.text.clear()
                toolBarEditText.visibility = View.GONE
                viewModel.onCancelClicked()
                requireActivity().invalidateOptionsMenu()
                true
            }

            R.id.add_button -> {
                viewModel.onAddClicked()
                true
            }

            R.id.filter_button -> {
                val direction = FeedFragmentDirections.toOptionsFragment()
                findNavController().navigate(direction)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setNoItemImageVisibility(binding: FeedFragmentBinding, recipeList: List<Recipe>?) {
        if (recipeList?.isEmpty() == true) binding.noResults.visibility = View.VISIBLE
        else binding.noResults.visibility = View.GONE
    }
}