package com.example.foodrecipes.ui.fragments.favorite

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_recipes.*
import java.io.Serializable
import java.util.ArrayList


class FavoriteAlertDialog : DialogFragment() {
    private val args by navArgs<FavoriteAlertDialogArgs>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val functionHolder= args.functionHolder
            builder.setTitle("Are you sure?")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialog, id ->
                    run {
                        functionHolder.onClick()
                        dialog.cancel()
                        val action=FavoriteAlertDialogDirections.actionFavoriteAlertDialogToFavoriteRecipesFragment()
                        findNavController().navigate(action)
                    }
                }
                .setNegativeButton("No") { dialog, id -> dialog.cancel() }

            return@let builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}