package com.example.foodrecipes.ui.adapters.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.data.utils.NetworkResult
import com.example.foodrecipes.ui.fragments.recipes.RecipesFragmentDirections
import java.lang.Error

class RecipesBindingAdapter {

    companion object{
        @BindingAdapter("onRecipeClick")
        @JvmStatic
        fun onRecipeItemClickListener(layout: ConstraintLayout, result:RecipeResult){
            layout.setOnClickListener {
                val action= RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                layout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("readApiResponseImage",
            "readDataImage",
            "searchedApiResponseImage",
            requireAll = true)
        @JvmStatic
        fun errorImageVieVisibility(view:ImageView,
                                    response: NetworkResult<FoodRecipeResponse>?,
                                    data:List<RecipeResult>?,
                                    search: NetworkResult<FoodRecipeResponse>?){
            if(response is NetworkResult.Error && data.isNullOrEmpty()){
                view.visibility= View.VISIBLE
            }else if(search is NetworkResult.Error){
                view.visibility= View.VISIBLE
            }else if(response is NetworkResult.Loading || search is NetworkResult.Loading){
                view.visibility= View.INVISIBLE
            }else if(response is NetworkResult.Success || search is NetworkResult.Success){
                view.visibility= View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponseText",
            "readDataText",
            "searchedApiResponseText",
            requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(view:TextView,
                                    response: NetworkResult<FoodRecipeResponse>?,
                                    data:List<RecipeResult>?,
                                    search: NetworkResult<FoodRecipeResponse>?){
            if(response is NetworkResult.Error && data.isNullOrEmpty()){
                view.visibility= View.VISIBLE
                view.text=response.message.toString()
            }else if(search is NetworkResult.Error){
                view.visibility= View.VISIBLE
                view.text=search.message.toString()
            }else if(response is NetworkResult.Loading || search is NetworkResult.Loading){
                view.visibility= View.INVISIBLE
            }else if(response is NetworkResult.Success || search is NetworkResult.Success){
                view.visibility= View.INVISIBLE
            }
        }
    }
}