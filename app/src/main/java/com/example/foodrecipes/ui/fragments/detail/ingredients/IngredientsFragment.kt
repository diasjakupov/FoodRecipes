package com.example.foodrecipes.ui.fragments.detail.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.ui.adapters.IngredientsAdapter
import kotlinx.android.synthetic.main.fragment_ingredients.*
import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : Fragment() {
    private val adapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView=inflater.inflate(R.layout.fragment_ingredients, container, false)
        val args: RecipeResult? =arguments?.getParcelable("recipeBundle")

        setupRecyclerView(mView)
        args?.extendedIngredients?.let { adapter.updateData(it) }
        return mView
    }

    private fun setupRecyclerView(mView:View){
        mView.ingredientsRV.adapter=adapter
        mView.ingredientsRV.layoutManager=LinearLayoutManager(this.context)
    }
}