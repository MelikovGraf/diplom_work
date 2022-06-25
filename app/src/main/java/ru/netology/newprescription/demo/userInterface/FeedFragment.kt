package ru.netology.newprescription.demo.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.newprescription.databinding.FeedFragmentBinding
import ru.netology.newprescription.demo.adapt.RecipeAdapter
import ru.netology.newprescription.demo.viewModel.ListViewModel

class FeedFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToRecipeDetailsScreen.observe(this) { recipe ->
            val direction = FeedFragmentDirections.toDetailsFragment(recipe.id)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipeAdapter(viewModel)

        binding.recipeRecyclerView.adapter = adapter

        viewModel.recipeList.observe(viewLifecycleOwner) { recipe ->
            adapter.submitList(recipe)
        }
    }.root
}