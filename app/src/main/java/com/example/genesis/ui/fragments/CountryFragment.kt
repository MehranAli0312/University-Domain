package com.example.genesis.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.genesis.adapter.CountryAdapter
import com.example.genesis.databinding.FragmentCountryBinding
import com.example.genesis.utils.Constants
import com.example.genesis.utils.DataStatus
import com.example.genesis.utils.setVisible
import com.example.genesis.utils.showLog
import com.example.genesis.utils.showToast
import com.example.genesis.viewmodel.CountryViewModel
import org.koin.android.ext.android.inject


class CountryFragment : Fragment() {

    private val binding by lazy {
        FragmentCountryBinding.inflate(layoutInflater)
    }


    private lateinit var navController: NavController

    private val viewModel: CountryViewModel by inject()

    private val countryAdapter by lazy {
        CountryAdapter {
            try {
                navController.navigate(
                    CountryFragmentDirections.actionCountryFragmentToUniversityDetailFragment(
                        it
                    )
                )
            } catch (ex: Exception) {
                showLog("${ex.message}")
            }
        }
    }

    private var mContext: Context? = null
    private var mActivity: Activity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as Activity
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

        binding.apply {
            setupRecyclerView()
            listenQuery()
        }
        observeData()
    }

    private fun FragmentCountryBinding.setupRecyclerView() {
        rvPhoto.apply {
            adapter = countryAdapter
        }
    }

    private fun FragmentCountryBinding.listenQuery() {
        binding.search.setOnClickListener {
            val countryName = edtQuestion.text.toString().trim()
            if (countryName.isEmpty()) {
                edtQuestion.error = "Please enter a country name."
            } else {
                val countryExists =
                    Constants.countriesNames.any { it.equals(countryName, ignoreCase = true) }
                if (countryExists) {
                    viewModel.getUniversities(countryName)
                    hideKeyboard()
                } else {
                    mContext?.showToast("Your country name doesn't match.")
                }
            }
        }
    }

    private fun observeData() {
        viewModel.universityList.observe(viewLifecycleOwner) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                    showProgressBar(isShownRV = false, isShownPb = true, false)
                }

                DataStatus.Status.SUCCESS -> {
                    showProgressBar(isShownRV = true, isShownPb = false, false)
                    binding.edtQuestion.setText("")
                    it.data?.let { it1 -> countryAdapter.differ.submitList(it1) }
                }

                DataStatus.Status.ERROR -> {
                    showProgressBar(isShownRV = false, isShownPb = false, true)
                    mContext?.showToast("There is something wrong!")
                }
            }
        }
    }

    private fun showProgressBar(isShownRV: Boolean, isShownPb: Boolean, isEmptyData: Boolean) {
        binding.apply {
            rvPhoto.setVisible(isShownRV)
            pBarLoading.setVisible(isShownPb)
            emptyDataPlaceHolder.setVisible(isEmptyData)
        }
    }

    private fun hideKeyboard() {
        val view = mActivity?.currentFocus
        if (view != null) {
            val imm =
                mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}