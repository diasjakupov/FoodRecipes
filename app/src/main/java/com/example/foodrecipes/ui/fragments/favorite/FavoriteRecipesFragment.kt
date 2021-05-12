package com.example.foodrecipes.ui.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentFavoriteRecipesBinding
import com.example.foodrecipes.ui.adapters.FavoriteAdapter
import com.example.foodrecipes.ui.adapters.RecipesAdapter
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
        adapter= FavoriteAdapter()
        binding.favoriteRV.adapter=adapter
        binding.favoriteRV.layoutManager=LinearLayoutManager(this.context)

        viewModel.favoriteEntities.observe(viewLifecycleOwner, {
            if(!it.isNullOrEmpty()){
                adapter.updateData(it)
            }
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}