package com.example.foodrecipes.ui.fragments.favorite

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.databinding.FragmentFavoriteRecipesBinding
import com.example.foodrecipes.ui.adapters.FavoriteAdapter
import com.example.foodrecipes.ui.adapters.RecipesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    private lateinit var adapter: FavoriteAdapter
    private val viewModel: FavoriteViewModel by viewModels()
    private var _binding: FragmentFavoriteRecipesBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel
        adapter= FavoriteAdapter(requireActivity(), viewModel)
        binding.favoriteRV.adapter=adapter
        binding.favoriteRV.layoutManager=LinearLayoutManager(this.context)
        setHasOptionsMenu(true)
        viewModel.favoriteEntities.observe(viewLifecycleOwner, {
            adapter.updateData(it)
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipe_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        viewModel.favoriteEntities.observe(viewLifecycleOwner, {
            if(it.isNullOrEmpty()){
                menu.findItem(R.id.deleteAllFavorites).isEnabled=false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.deleteAllFavorites){
                viewModel.deleteAllFavorite()
                Snackbar.make(binding.root, "All recipes removed", Snackbar.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        adapter.closeActionMode()
    }
}