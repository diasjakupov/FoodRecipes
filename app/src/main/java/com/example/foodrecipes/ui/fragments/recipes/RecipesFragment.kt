package com.example.foodrecipes.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.data.utils.NetworkListener
import com.example.foodrecipes.data.utils.NetworkResult
import com.example.foodrecipes.data.utils.observeOnce
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.ui.adapters.RecipesAdapter
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by navArgs<RecipesFragmentArgs>()
    private val viewModel: RecipesFragmentViewModel by activityViewModels()
    private lateinit var networkListener: NetworkListener
    private lateinit var adapter: RecipesAdapter
    private lateinit var rvShimmer: ShimmerRecyclerView
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        rvShimmer = binding.recipeRv
        adapter = RecipesAdapter()
        setupRecyclerView()
        setHasOptionsMenu(true)

        viewModel.readOnlineStatus.observe(viewLifecycleOwner, {
            viewModel.backOnline = it
        })

        binding.floatingActionButton.setOnClickListener {
            if (viewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            } else {
                viewModel.showNetworkStatus()
            }
        }
        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkConnection(requireContext()).collect {
                Log.e("TAG", it.toString())
                viewModel.networkStatus = it
                viewModel.showNetworkStatus()
                getRecipesEntities()
            }
        }


        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchApiResponse(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private suspend fun requestApiResponse() {
        viewModel.getRecipes(viewModel.applyQueries())
        viewModel.recipeResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    disableShimmerEffect()
                    response.data?.let { adapter.updateData(it.results.sortedByDescending { value->value.aggregateLikes }) }
                }
                is NetworkResult.Error -> {
                    disableShimmerEffect()
                    Toast.makeText(
                        this.context,
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun searchApiResponse(search: String) {
        showShimmerEffect()
        viewModel.searchRecipes(viewModel.applySearchQueries(search))
        viewModel.searchedRecipesResponse.observe(viewLifecycleOwner, { response ->
            Log.e("TAG", "${response is NetworkResult.Error}")
            when (response) {
                is NetworkResult.Success -> {
                    disableShimmerEffect()
                    response.data?.let { adapter.updateData(it.results) }
                }
                is NetworkResult.Error -> {
                    disableShimmerEffect()
                    if (response.data?.results?.size == 0 || response.data?.results?.size == null) {
                        adapter.updateData(emptyList())
                    }
                    Toast.makeText(
                        this.context,
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun getRecipesEntities() {
        viewModel.recipeEntities.observeOnce(viewLifecycleOwner, {
            if (!args.isFromBottomSheet && it.isNotEmpty()) {
                Log.e("TAG", "get database")
                adapter.updateData(it)
                disableShimmerEffect()
            } else {
                Log.e("TAG", "request")
                lifecycleScope.launch {
                    requestApiResponse()
                }
            }
        })

    }

    private fun setupRecyclerView() {
        rvShimmer.adapter = adapter
        rvShimmer.layoutManager = LinearLayoutManager(this.context)
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        rvShimmer.showShimmer()
    }

    private fun disableShimmerEffect() {
        rvShimmer.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}