package com.example.foodrecipes.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.foodrecipes.R
import com.example.foodrecipes.ui.adapters.PagerAdapter
import com.example.foodrecipes.ui.fragments.detail.ingredients.IngredientsFragment
import com.example.foodrecipes.ui.fragments.detail.instruction.InstructionsFragment
import com.example.foodrecipes.ui.fragments.detail.overview.OverviewFragment
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {
    private lateinit var pagerAdapter:PagerAdapter
    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(detailsToolBar)
        detailsToolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recipeBundle=Bundle().apply {
            putParcelable("recipeBundle", args.result)
        }

        pagerAdapter=PagerAdapter(
            data=recipeBundle,
            fragments = initFragments(),
            titles = initFragmentsTitles(),
            manager = supportFragmentManager
        )

        detailsViewPager.adapter=pagerAdapter
        detailsTabLayout.setupWithViewPager(detailsViewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initFragments(): ArrayList<Fragment>{
        return arrayListOf(OverviewFragment(), IngredientsFragment(), InstructionsFragment())
    }

    private fun initFragmentsTitles(): ArrayList<String>{
        return arrayListOf("Overview", "Ingredients", "Instruction")
    }
}