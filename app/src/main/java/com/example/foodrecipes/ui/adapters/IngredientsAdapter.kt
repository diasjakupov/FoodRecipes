package com.example.foodrecipes.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodrecipes.R
import com.example.foodrecipes.data.db.models.ExtendedIngredient
import com.example.foodrecipes.data.utils.Constant.BASE_IMAGE_URL
import com.example.foodrecipes.data.utils.RecipeDiffUtils
import kotlinx.android.synthetic.main.ingredients_item.view.*
import java.util.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private var ingredientsList= emptyList<ExtendedIngredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.ingredients_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.ingredientImageView
            .load(BASE_IMAGE_URL + ingredientsList[position].image){
                crossfade(600)
                error(R.drawable.ic_error)
            }
        holder.itemView.ingredientTitle.text=ingredientsList[position].name?.capitalize(Locale.ROOT)
        holder.itemView.ingredientAmount.text=ingredientsList[position].amount.toString()
        holder.itemView.ingredientUnit.text=ingredientsList[position].unit
        holder.itemView.ingredient_consistency.text=ingredientsList[position].consistency
        holder.itemView.ingredient_original.text=ingredientsList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun updateData(data:List<ExtendedIngredient>){
        val recipeDiffUtil=RecipeDiffUtils(ingredientsList, data)
        val diffUtilRes=DiffUtil.calculateDiff(recipeDiffUtil)
        ingredientsList=data
        diffUtilRes.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}