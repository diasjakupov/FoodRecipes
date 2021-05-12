package com.example.foodrecipes.ui.activities.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import androidx.room.PrimaryKey
import com.example.foodrecipes.R
import com.example.foodrecipes.data.db.models.ExtendedIngredient
import com.example.foodrecipes.data.db.models.entities.FavoriteRecipe
import com.example.foodrecipes.ui.adapters.PagerAdapter
import com.example.foodrecipes.ui.fragments.detail.ingredients.IngredientsFragment
import com.example.foodrecipes.ui.fragments.detail.instruction.InstructionsFragment
import com.example.foodrecipes.ui.fragments.detail.overview.OverviewFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: PagerAdapter
    private val args by navArgs<DetailsActivityArgs>()
    private val viewModel: DetailViewModel by viewModels()
    private var isSaved: Boolean = false
    private var favoriteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(detailsToolBar)
        detailsToolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recipeBundle = Bundle().apply {
            putParcelable("recipeBundle", args.result)
        }

        pagerAdapter = PagerAdapter(
            data = recipeBundle,
            fragments = initFragments(),
            titles = initFragmentsTitles(),
            manager = supportFragmentManager
        )

        detailsViewPager.adapter = pagerAdapter
        detailsTabLayout.setupWithViewPager(detailsViewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        checkFavorites(menu!!.findItem(R.id.save_to_favorite))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorite && !isSaved) {
            saveToFavorite(item)
        } else if (item.itemId == R.id.save_to_favorite && isSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorite(item: MenuItem) {
        val recipeItem = args.result
        viewModel.saveToFavorite(
            FavoriteRecipe(
                result = recipeItem
            )
        )
        applyColorToMenu(item, R.color.yellow)
        isSaved = true
        Snackbar.make(
            detailsLayout, "Recipe has been saved", Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun removeFromFavorites(item: MenuItem) {
        viewModel.deleteFromFavoriteById(args.result.id)
        applyColorToMenu(item, R.color.white)
        isSaved = false
        Snackbar.make(
            detailsLayout, "Recipe has been deleted", Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun initFragments(): ArrayList<Fragment> {
        return arrayListOf(OverviewFragment(), IngredientsFragment(), InstructionsFragment())
    }

    private fun initFragmentsTitles(): ArrayList<String> {
        return arrayListOf("Overview", "Ingredients", "Instruction")
    }

    private fun checkFavorites(item: MenuItem) {
        viewModel.checkFavorites(args.result).observe(this, {
            if (it) {
                applyColorToMenu(item, R.color.yellow)
                isSaved = true
            } else {
                applyColorToMenu(item, R.color.white)
                isSaved = false
            }
        })

    }

    private fun applyColorToMenu(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}