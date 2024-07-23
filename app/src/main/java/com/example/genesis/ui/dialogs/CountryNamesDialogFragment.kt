package com.example.genesis.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.genesis.R
import com.example.genesis.adapter.CountryAdapter
import com.example.genesis.databinding.FragmentCountryNamesBinding
import com.example.genesis.utils.Constants.countriesNames
import com.example.genesis.utils.Constants.countryNameKey
import com.example.genesis.utils.Constants.requestKey
import com.example.genesis.utils.showLog
import com.example.genesis.viewmodel.CountryViewModel
import org.koin.android.ext.android.inject


// DialogFragment to display a list of country names
class CountryNamesDialogFragment : DialogFragment() {

    // View binding for the fragment
    private val binding by lazy { FragmentCountryNamesBinding.inflate(layoutInflater) }

    // NavController for navigating between fragments
    private lateinit var navController: NavController

    // Adapter for the RecyclerView to display country names
    private val countryAdapter by lazy {
        CountryAdapter(countriesNames) {
            try {
                // Set the fragment result with the selected country name
                setFragmentResult(requestKey, bundleOf(countryNameKey to it))
                // Navigate up in the navigation stack
                navController.navigateUp()
            } catch (ex: Exception) {
                // Log any exceptions that occur
                showLog("${ex.message}")
            }
        }
    }

    // Context and Activity references
    private lateinit var mContext: Context
    private lateinit var mActivity: AppCompatActivity

    // Called when the fragment is attached to its context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as AppCompatActivity
    }

    // Called when the fragment's view is about to be displayed
    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        (activity)?.let {
            it.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels

            // Set the dialog's size to 90% width and 60% height of the screen
            dialog?.window?.setLayout((width * .9).toInt(), (height * .6).toInt())
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
        }
    }

    // Called to create the dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    // Called when the fragment is being created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the NavController
        navController = findNavController()
    }

    // Called to create the fragment's view hierarchy
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Return the root view of the binding
        return binding.root
    }

    // Called after the fragment's view has been created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews() // Initialize the views in the fragment
    }

    // Extension function to initialize the views
    private fun FragmentCountryNamesBinding.initViews() {
        closeBtn.setOnClickListener {
            // Navigate up when the close button is clicked
            navController.navigateUp()
        }

        rvCountries.apply {
            // Set the adapter for the RecyclerView
            adapter = countryAdapter
        }
    }
}
