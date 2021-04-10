package com.example.foodrecipes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodrecipes.R
import kotlinx.android.synthetic.main.fragment_recipes.view.*


class RecipesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_recipes, container, false)
        view.recipe_rv.showShimmer()
        return view
    }
}