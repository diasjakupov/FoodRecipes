package com.example.foodrecipes.ui.adapters

import android.os.Parcelable
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import com.example.foodrecipes.data.utils.RecipeDiffUtils
import com.example.foodrecipes.databinding.FavoriteItemBinding
import com.example.foodrecipes.databinding.RecipeItemBinding
import com.example.foodrecipes.ui.fragments.favorite.FavoriteAlertDialog
import com.example.foodrecipes.ui.fragments.favorite.FavoriteRecipesFragmentDirections
import com.example.foodrecipes.ui.fragments.favorite.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.favorite_item.view.*
import java.io.Serializable

class FavoriteAdapter(
    private val activity: FragmentActivity,
    private val viewModel: FavoriteViewModel
): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(), ActionMode.Callback {

    private var multiSelection=false
    private var selectedRecipes= arrayListOf<FavoriteRecipe>()
    private var holders= arrayListOf<ViewHolder>()
    private var recipe= emptyList<FavoriteRecipe>()
    private lateinit var mActionMode: ActionMode
    private lateinit var mView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holders.add(holder)
        mView=holder.itemView.rootView
        val currentResult=recipe[position]
        holder.bind(currentResult)
        holder.itemView.recipeItem.setOnClickListener {
            Log.e("TAG", "one click")
            if(multiSelection){
                Log.e("TAG", "one click $multiSelection")
                applySelection(holder, currentResult)
            }else{
                Log.e("TAG", "one click $multiSelection")
                val action= FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(currentResult.result)
                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.itemView.recipeItem.setOnLongClickListener {
            Log.e("TAG", "long click")
            if(!multiSelection) {
                Log.e("TAG", "long click $multiSelection")
                multiSelection = true
                activity.startActionMode(this)
                applySelection(holder, currentResult)
                true
            }else{
                false
            }

        }
    }

    override fun getItemCount(): Int {
        return recipe.size
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode!!.menuInflater.inflate(R.menu.contextual_menu, menu)
        mActionMode=mode
        applyStatusBarColor(R.color.contextualColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if(item?.itemId==R.id.deleteFavoriteItem){
            val action=FavoriteRecipesFragmentDirections
                .actionFavoriteRecipesFragmentToFavoriteAlertDialog(
                    selectedRecipes.map { it.result.id }.toIntArray()
                    ,functionHolder( { mode?.finish() }, {selectedRecipes.clear()})
                )
            mView.findNavController().navigate(action)
            multiSelection=false
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        holders.forEach {
            applyColorToSelectedItem(it, R.color.cardBackground, R.color.strokeColor)
        }
        multiSelection=false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applySelection(holder: ViewHolder, recipe: FavoriteRecipe){
        if(selectedRecipes.contains(recipe)){
            selectedRecipes.remove(recipe)
            applyColorToSelectedItem(holder, R.color.cardBackground, R.color.strokeColor)
            applyTitle()
        }else{
            selectedRecipes.add(recipe)
            applyColorToSelectedItem(holder, R.color.lightColorPrimary, R.color.colorPrimary)
            applyTitle()
        }
    }

    private fun applyColorToSelectedItem(holder:ViewHolder, bg:Int, strokeColor: Int){
        holder.itemView.recipeItem.setBackgroundColor(ContextCompat.getColor(activity, bg))
        holder.itemView.itemCardView.strokeColor = ContextCompat.getColor(activity, strokeColor)
    }

    private fun applyStatusBarColor(color:Int){
        activity.window.statusBarColor=ContextCompat.getColor(activity, color)
    }


    fun closeActionMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }

    fun updateData(newRecipes:List<FavoriteRecipe>){
        val recipesDiffUtils= RecipeDiffUtils(recipe, newRecipes)
        val diffUtilResult= DiffUtil.calculateDiff(recipesDiffUtils)
        recipe=newRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun applyTitle(){
        when(selectedRecipes.size){
            0->{
                mActionMode.finish()
            }
            1->{
                mActionMode.title="1 item selected"
            }
            else->{mActionMode.title="${selectedRecipes.size} items selected"}

        }

    }

    class ViewHolder(private val binding: FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: FavoriteRecipe){
            binding.result=result.result
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding= FavoriteItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class functionHolder(val onClick: ()->Unit, val clearList: ()->Unit): Serializable
}