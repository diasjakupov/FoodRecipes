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
import com.example.foodrecipes.ui.adapters.RecipesAdapter
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val viewModel:RecipesFragmentViewModel by viewModels()
    private lateinit var mView:View
    private lateinit var adapter: RecipesAdapter
    private lateinit var rvShimmer: ShimmerRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_recipes, container, false)


        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvShimmer=mView.recipe_rv
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

}