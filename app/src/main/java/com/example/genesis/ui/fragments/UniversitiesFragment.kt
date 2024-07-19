package com.example.genesis.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.genesis.adapter.UniversityAdapter
import com.example.genesis.databinding.FragmentUniversityBinding
import com.example.genesis.utils.Constants
import com.example.genesis.utils.Constants.countryNameKey
import com.example.genesis.utils.Constants.requestKey
import com.example.genesis.utils.DataStatus
import com.example.genesis.utils.setVisible
import com.example.genesis.utils.showLog
import com.example.genesis.utils.showToast
import com.example.genesis.utils.showToastLong
import com.example.genesis.viewmodel.CountryViewModel
import org.koin.android.ext.android.inject


class UniversitiesFragment : Fragment() {

    private val binding by lazy {
        FragmentUniversityBinding.inflate(layoutInflater)
    }


    private lateinit var navController: NavController

    private val viewModel: CountryViewModel by inject()

    private val universityAdapter by lazy {
        UniversityAdapter {
            try {
                navController.navigate(
                    UniversitiesFragmentDirections.actionUniversityFragmentToUniversityDetailFragment(
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
        listenResultListener()
    }

    private fun listenResultListener() {
        // Set up the result listener
        setFragmentResultListener(requestKey) { _, bundle ->
            val result = bundle.getString(countryNameKey)
            result?.let {
                binding.edtQuestion.setText(it)
            }
        }
    }

    private fun FragmentUniversityBinding.setupRecyclerView() {
        rvUniversities.apply {
            adapter = universityAdapter
        }
    }

    private fun FragmentUniversityBinding.listenQuery() {
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
                    navController.navigate(UniversitiesFragmentDirections.actionUniversityFragmentToCountryNameDialogFragment())
                }
            }
        }
    }

    private fun observeData() {
        viewModel.universityList.observe(viewLifecycleOwner) {
            when (it) {
                is DataStatus.Loading -> {
                    showProgressBar(isShownRV = false, isShownPb = true, false)
                }

                is DataStatus.Success -> {
                    showProgressBar(isShownRV = true, isShownPb = false, false)
                    binding.edtQuestion.setText("")
                    it.data?.let { it1 -> universityAdapter.differ.submitList(it1) }
                }

                is DataStatus.Error -> {
                    showProgressBar(isShownRV = false, isShownPb = false, true)
                    mContext?.showToastLong(it.error)
                }
            }
        }
    }

    private fun showProgressBar(isShownRV: Boolean, isShownPb: Boolean, isEmptyData: Boolean) {
        binding.apply {
            rvUniversities.setVisible(isShownRV)
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