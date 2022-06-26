package ru.netology.newprescription.demo.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.newprescription.activity.CookingStage
import ru.netology.newprescription.databinding.CookingStageEditorBinding
import ru.netology.newprescription.demo.adapter.display.CookingStageService

class CookingStageEditorFragment : Fragment() {

    private val args = navArgs<CookingStageEditorFragmentArgs>()

    private val cookingStepService = CookingStageService


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = CookingStageEditorBinding.inflate(layoutInflater, container, false).also { binding ->

        if (args.value !== null) {
            val step = args.value.cookingStage
            binding.descriptionStageEditText.setText(step?.descript)
            if (step?.uri !== null) {
                binding.addPreview.visibility = View.GONE
                binding.stagePreview
            } else {
                binding.clearPreview.visibility = View.GONE
            }
        }

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cancelEditStepButton.setOnClickListener {
            binding.descriptionStageEditText.text?.clear()
        }

        binding.addPreview.setOnClickListener {
//            TODO()
        }

        binding.okButton.setOnClickListener {
            val newDescription = binding.descriptionStageEditText.text
            if (!newDescription.isNullOrBlank()) {
                val step = CookingStage(
                    descript = newDescription.toString(),
                    uri = null,
                    id = args.value.cookingStage?.id ?: -1
                )
                cookingStepService.addCookingStep(step)
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    context,
                    "There is no description, the field is empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }.root

    companion object {
        const val RESULT_KEY_NEW_STAGE = "add new stage"
        const val ORDER_KEY = "order"
    }
}