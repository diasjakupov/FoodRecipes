package com.example.foodrecipes.ui.adapters.binding

import android.util.Log
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


        @JvmStatic
        @BindingAdapter("checkDataValid", requireAll = true)
         fun checkDataValid(
             view: View,
             data: List<FavoriteRecipe>?
         ){
            Log.e("TAG", "$data from bindning")
            if (data != null) {
                if(data.isEmpty()){
                    view.visibility=View.VISIBLE
                }else{
                    view.visibility=View.INVISIBLE
                }
            }

         }
    }
}