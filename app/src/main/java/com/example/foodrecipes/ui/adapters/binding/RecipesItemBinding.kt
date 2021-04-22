package com.example.foodrecipes.ui.adapters.binding

import android.view.ContextMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foodrecipes.R

class RecipesItemBinding {
    companion object{

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

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan:Boolean){
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
            }
        }
    }
}