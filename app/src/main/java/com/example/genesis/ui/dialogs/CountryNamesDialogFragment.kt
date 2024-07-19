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

class CountryNamesDialogFragment : DialogFragment() {

    private val binding by lazy { FragmentCountryNamesBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController



    private val countryAdapter by lazy {
        CountryAdapter(countriesNames) {
            try {
                setFragmentResult(requestKey, bundleOf(countryNameKey to it))
                navController.navigateUp()
            } catch (ex: Exception) {
                showLog("${ex.message}")
            }
        }
    }

    private lateinit var mContext: Context
    private lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as AppCompatActivity
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        (activity)?.let {
            it.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels

            dialog?.window?.setLayout((width * .9).toInt(), (height * .6).toInt())
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
    }

    private fun FragmentCountryNamesBinding.initViews() {
        closeBtn.setOnClickListener {
            navController.navigateUp()
        }

        rvCountries.apply {
            adapter = countryAdapter
        }

    }
}