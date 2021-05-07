package com.example.foodrecipes.ui.adapters.binding

import android.view.ContextMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foodrecipes.R
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.ui.fragments.recipes.RecipesFragmentDirections
import org.jsoup.Jsoup

class ItemBinding {
    companion object{

        @BindingAdapter("onItemClick")
        @JvmStatic
        fun onItemClickListener(layout: ConstraintLayout, result:RecipeResult){
            layout.setOnClickListener {
                val action=RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                layout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(view:TextView, value:Int){
            view.text=value.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(view:TextView, value:Int){
            view.text=value.toString()
        }

        @BindingAdapter("applyColor")
        @JvmStatic
        fun applyColor(view: View, vegan:Boolean){
            if(vegan){
                when(view){
                    is TextView->{
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView ->{
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }
        }

        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView:ImageView, url:String){
            imageView.load(url){
                crossfade(600)
                error(R.drawable.ic_error)
            }
        }

        @BindingAdapter("setDetailSummary")
        @JvmStatic
        fun setSummary(view:TextView, data:String?){
            if(data !=null){
                val desc=Jsoup.parse(data).text()
                view.text=desc
            }

        }
    }
}