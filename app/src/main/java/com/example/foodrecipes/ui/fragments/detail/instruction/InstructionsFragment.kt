package com.example.foodrecipes.ui.fragments.detail.instruction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.foodrecipes.R
import com.example.foodrecipes.data.db.models.entities.RecipeResult
import kotlinx.android.synthetic.main.fragment_instructions.view.*

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView=inflater.inflate(R.layout.fragment_instructions, container, false)
        val args: RecipeResult? =arguments?.getParcelable("recipeBundle")
        mView.instructionWebView.webViewClient= object : WebViewClient() {}
        args?.sourceUrl?.let { mView.instructionWebView.loadUrl(it) }
        return mView
    }

}