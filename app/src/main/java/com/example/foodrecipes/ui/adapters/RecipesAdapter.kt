package com.example.foodrecipes.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.data.db.models.FoodRecipeResponse
import com.example.foodrecipes.data.db.models.Result
import com.example.foodrecipes.databinding.RecipeItemBinding

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var recipe= emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentResult=recipe[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return recipe.size
    }

    fun updateData(newRecipes:FoodRecipeResponse){
        recipe=newRecipes.results
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result:Result){
            binding.result=result
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent:ViewGroup): ViewHolder{
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=RecipeItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}