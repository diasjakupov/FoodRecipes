package com.example.foodrecipes.data.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.foodrecipes.data.db.models.RecipeResult

class RecipeDiffUtils(
        private val oldList:List<RecipeResult>,
        private val newList:List<RecipeResult>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}