package com.example.foodrecipes.ui.fragments.joke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodrecipes.R
import com.example.foodrecipes.data.utils.NetworkResult
import com.example.foodrecipes.databinding.FragmentJokeBinding
import com.example.foodrecipes.ui.fragments.detail.instruction.LoadingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JokeFragment : Fragment() {

    private val viewModel: FoodJokeViewModel by viewModels()
    private var _binding: FragmentJokeBinding? = null
    private lateinit var loading:LoadingFragment
    private val binding get() = _binding!!
    private var foodJoke=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentJokeBinding.inflate(inflater, container, false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        loading= LoadingFragment()
        setHasOptionsMenu(true)
        viewModel.getFoodJoke()
        viewModel.foodJokeResponse.observe(viewLifecycleOwner, {
            when(it){
                is NetworkResult.Success->{
                    dismissLoadingFragment()
                    binding.foodJokeTV.text=it.data?.text
                    if(it.data?.text!=null){
                        foodJoke= it.data.text
                    }
                }
                is NetworkResult.Error->{
                    dismissLoadingFragment()
                    loadLocalCache()
                    Toast.makeText(this.context, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading->{
                    setupProgressBar()
                }
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.shareJoke){
            val intent= Intent().apply {
                this.action=Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                this.type="text/plain"
            }
            startActivity(intent)
        }
        return true
    }

    private fun loadLocalCache(){
        lifecycleScope.launch {
            viewModel.foodJokeEntity.observe(viewLifecycleOwner, {
                if(!it.isNullOrEmpty()){
                    binding.foodJokeTV.text=it.first().text
                    foodJoke=it.first().text
                }else{
                    binding.materialCardView.visibility=View.INVISIBLE
                }
            })
        }
    }

    private fun setupProgressBar(){
        binding.materialCardView.visibility=View.INVISIBLE
        childFragmentManager.beginTransaction().replace(
            R.id.foodJokeFL,
            loading
        ).commit()
    }

    private fun dismissLoadingFragment(){
        binding.materialCardView.visibility=View.VISIBLE
        childFragmentManager.beginTransaction().remove(loading).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}