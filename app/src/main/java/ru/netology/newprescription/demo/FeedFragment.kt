package ru.netology.newprescription.demo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.newprescription.activity.Recipe
import ru.netology.newprescription.databinding.FeedFragmentBinding

class FeedFragment : Fragment() {

    private val viewModel: Pattern by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        viewModel.recipeList.observe(viewLifecycleOwner) {
            Log.d("TAG", it.toString())
        }
        viewModel.addRecipe(Recipe(1, "favourites", "Russian", ""))
        viewModel.deleteRecipe(Recipe(3,"Muhammara","Oriental cuisine", ""))
        viewModel.editRecipe(Recipe(5,"Beef Lagman","Asian cuisine", ""))
    }.root
}