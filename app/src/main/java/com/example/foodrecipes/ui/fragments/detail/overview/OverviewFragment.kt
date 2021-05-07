package com.example.foodrecipes.ui.fragments.detail.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.foodrecipes.R
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.databinding.FragmentOverviewBinding
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import kotlinx.android.synthetic.main.fragment_overview.view.*


class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentOverviewBinding.inflate(inflater, container, false)
        val bundle: RecipeResult? = arguments?.getParcelable("recipeBundle")
        if(bundle != null){
            binding.result=bundle
        }
        return binding.root
    }

}