package ru.netology.newprescription.demo.userInterface

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.netology.newprescription.R
import ru.netology.newprescription.activity.CookingStage
import ru.netology.newprescription.databinding.CookingStageEditorBinding
import ru.netology.newprescription.demo.adapter.display.CookingStageService

class CookingStageEditorFragment : Fragment() {

    private val args = navArgs<CookingStageEditorFragmentArgs>()

    private val cookingStepService = CookingStageService

    private var selectedCookingStepImageUri: Uri? = null

    private val selectImages: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            selectedCookingStepImageUri = imageUri
            imageCookingStageUri = imageUri
            view?.findViewById<ImageView>(R.id.stage_preview)?.setImageURI(imageUri)
            view?.findViewById<ImageButton>(R.id.add_preview_button)?.visibility = View.GONE
            view?.findViewById<ImageButton>(R.id.clear_preview_button)?.visibility = View.VISIBLE
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = CookingStageEditorBinding.inflate(layoutInflater, container, false).also { binding ->

        if (args.value !== null) {
            val step = args.value.cookingStage
            binding.descriptionStageEditText.setText(step?.descript)
            if (step?.stageImageUri == null) {
                binding.stagePreview.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
                binding.addPreviewButton.visibility = View.VISIBLE
                binding.clearPreviewButton.visibility = View.GONE
            } else {
                imageCookingStagePreviewTag = null
                binding.addPreviewButton.visibility = View.GONE
                binding.clearPreviewButton.visibility = View.VISIBLE
                Glide.with(binding.stagePreview)
                    .asDrawable()
                    .load(step.stageImageUri)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(binding.stagePreview)
            }
        } else {
            binding.clearPreviewButton.visibility = View.GONE
        }

        binding.clearPreviewButton.setOnClickListener {
            binding.stagePreview.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
            binding.addPreviewButton.visibility = View.VISIBLE
            binding.clearPreviewButton.visibility = View.GONE
            imageCookingStageUri = null
            imageCookingStagePreviewTag = imageCookingStagePreviewIsEmptyTag
        }

        binding.cancelButton.setOnClickListener {
            imageCookingStageUri = null
            imageCookingStagePreviewTag = null
            findNavController().popBackStack()
        }

        binding.cancelEditStepButton.setOnClickListener {
            imageCookingStageUri = null
            imageCookingStagePreviewTag = null
            binding.descriptionStageEditText.text?.clear()
        }

        binding.addPreviewButton.setOnClickListener {
            selectImages.launch(RESULT_IMAGES)
        }

        binding.okButton.setOnClickListener {
            val newDescription = binding.descriptionStageEditText.text
            if (!newDescription.isNullOrBlank()) {
                val step = CookingStage(
                    descript = newDescription.toString(),
                    stageImageUri = when {
                        imageCookingStageUri !== null -> imageCookingStageUri
                        imageCookingStagePreviewTag== imageCookingStagePreviewIsEmptyTag -> null
                        args.value.cookingStage?.stageImageUri !== null -> args.value.cookingStage?.stageImageUri
                        else -> null
                    },
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
        const val RESULT_IMAGES = "image/*"
        var imageCookingStageUri: Uri? = null
        var imageCookingStagePreviewTag : String? = null
        const val imageCookingStagePreviewIsEmptyTag = "empty image preview"
    }
}