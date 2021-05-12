package com.example.foodrecipes.ui.adapters.binding

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.ui.fragments.favorite.FavoriteRecipesFragmentDirections
import com.example.foodrecipes.ui.fragments.recipes.RecipesFragmentDirections

class FavoriteBinding {
    companion object{

        @BindingAdapter("onFavoriteClick")
        @JvmStatic
        fun onFavoriteItemClickListener(layout: ConstraintLayout, value: RecipeResult){
            layout.setOnClickListener {
                val action= FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(value)
                layout.findNavController().navigate(action)
            }
        }

        @JvmStatic
        @BindingAdapter("checkDataValid", requireAll = true)
         fun checkDataValid(
             view: View,
             data: List<FavoriteRecipe>?
         ){
                if(data.isNullOrEmpty()){
                    view.visibility=View.VISIBLE
                }else{
                    view.visibility=View.INVISIBLE
                }

         }
    }
}