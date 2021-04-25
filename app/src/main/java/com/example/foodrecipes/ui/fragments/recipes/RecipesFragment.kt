package com.example.foodrecipes.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.data.utils.NetworkResult
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.ui.adapters.RecipesAdapter
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val viewModel:RecipesFragmentViewModel by viewModels()
    private lateinit var adapter: RecipesAdapter
    private lateinit var rvShimmer: ShimmerRecyclerView
    private var _binding: FragmentRecipesBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this
        binding.viewmodel=viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvShimmer=binding.recipeRv
        adapter= RecipesAdapter()
        setupRecyclerView()
        getRecipesEntities()
    }

    private fun requestApiResponse(){
        viewModel.getRecipes(viewModel.applyQueries())
        viewModel.recipeResponse.observe(viewLifecycleOwner, {response->
            when(response){
                is NetworkResult.Success->{
                    disableShimmerEffect()
                    response.data?.let { adapter.updateData(it.results) }
                }
                is NetworkResult.Error->{
                    disableShimmerEffect()
                    Toast.makeText(
                            this.context,
                            response.message.toString(),
                            Toast.LENGTH_SHORT)
                            .show()
                }
                is NetworkResult.Loading->{
                    showShimmerEffect()
                }
            }
        })
    }

    private fun getRecipesEntities(){
        viewModel.recipeEntities.observe(viewLifecycleOwner, {
            if(it.isEmpty()){
                Log.e("TAG", "request")
                requestApiResponse()
            }else{
                Log.e("TAG", "get database")
                adapter.updateData(it)
                disableShimmerEffect()
            }
        })
    }
    private fun setupRecyclerView(){
        rvShimmer.adapter=adapter
        rvShimmer.layoutManager=LinearLayoutManager(this.context)
        showShimmerEffect()
    }

    private fun showShimmerEffect(){
        rvShimmer.showShimmer()
    }

    private fun disableShimmerEffect(){
        rvShimmer.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}