package com.example.foodrecipes.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.foodrecipes.R
import com.example.foodrecipes.data.utils.Constant.DEFAULT_DIET_TYPE
import com.example.foodrecipes.data.utils.Constant.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.ui.fragments.recipes.RecipesFragmentDirections
import com.example.foodrecipes.ui.fragments.recipes.RecipesFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_recipes_bottom_sheet.view.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*


class RecipesBottomSheet : BottomSheetDialogFragment() {
    private val viewModel:RecipesFragmentViewModel by activityViewModels()
    private var mealTypeChip: String = DEFAULT_MEAL_TYPE
    private var mealTypeChipId= 0
    private var dietTypeChip: String = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0
    private val TAG = "RecipesBottomSheet"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView=inflater.inflate(R.layout.fragment_recipes_bottom_sheet, container, false)

        viewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner, {
            mealTypeChip=it.selectedMealType
            dietTypeChip=it.selectedDietType
            updateChip(it.selectedMealTypeId, mView.mealTypeChipGroup)
            updateChip(it.selectedDietTypeId, mView.dietTypeChipGroup)
        })

        mView.mealTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip=group.findViewById<Chip>(checkedId)
            val selectedMealType=chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChip=selectedMealType
            mealTypeChipId= checkedId
        }

        mView.dietTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip=group.findViewById<Chip>(checkedId)
            val selectedDietType=chip.text.toString().toLowerCase(Locale.ROOT)
            Log.e(TAG, "$selectedDietType + $chip")
            dietTypeChip=selectedDietType
            dietTypeChipId= checkedId
        }

        mView.apply_btn.setOnClickListener {
            Log.e("TAG", "saving $mealTypeChip and $dietTypeChip")
            lifecycleScope.launch {
                viewModel.saveMealAndDietType(mealType=mealTypeChip,
                    mealTypeId = mealTypeChipId,
                    dietType = dietTypeChip,
                    dietTypeId = dietTypeChipId)
            }.invokeOnCompletion {
                val action=RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment()
                action.isFromBottomSheet=true
                findNavController().navigate(action)
            }
        }

        return mView
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if(chipId != 0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked=true
            }catch (e:Exception){
                Log.e(TAG, e.toString())
            }
        }

    }

}