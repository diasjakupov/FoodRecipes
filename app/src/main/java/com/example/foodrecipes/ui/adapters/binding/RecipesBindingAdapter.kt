package com.example.foodrecipes.ui.adapters.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.data.utils.NetworkResult
import java.lang.Error

class RecipesBindingAdapter {

    companion object{
        @BindingAdapter("readApiResponseImage", "readDataImage", requireAll = true)
        @JvmStatic
        fun errorImageVieVisibility(view:ImageView,
                                    response: NetworkResult<FoodRecipeResponse>?,
                                    data:List<RecipeResult>?){
            if(response is NetworkResult.Error && data.isNullOrEmpty()){
                view.visibility= View.VISIBLE
            }else if(response is NetworkResult.Loading){
                view.visibility= View.INVISIBLE
            }else if(response is NetworkResult.Success){
                view.visibility= View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponseText", "readDataText", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(view:TextView,
                                    response: NetworkResult<FoodRecipeResponse>?,
                                    data:List<RecipeResult>?){
            if(response is NetworkResult.Error && data.isNullOrEmpty()){
                view.visibility= View.VISIBLE
                view.text=response.message.toString()
            }else if(response is NetworkResult.Loading){
                view.visibility= View.INVISIBLE
            }else if(response is NetworkResult.Success){
                view.visibility= View.INVISIBLE
            }
        }
    }
}