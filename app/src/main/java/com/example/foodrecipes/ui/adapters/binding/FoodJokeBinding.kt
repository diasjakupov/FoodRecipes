package com.example.foodrecipes.ui.adapters.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipes.data.db.models.FoodJoke
import com.example.foodrecipes.data.utils.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {

    companion object {

        @BindingAdapter("readApiFoodJoke", "readDatabaseFoodJoke", requireAll = true)
        @JvmStatic
        fun setErrorView(
            view: View,
            response: NetworkResult<FoodJoke>?,
            data: List<FoodJoke>?
        ) {
            if(data != null){
                if(data.isEmpty()){
                    view.visibility=View.VISIBLE
                    if(view is TextView){
                        if(response != null){
                            view.text=response.message.toString()
                        }
                    }
                }
            }
            if(response is NetworkResult.Success){
                view.visibility=View.INVISIBLE
            }
        }
    }
}