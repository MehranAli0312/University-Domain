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

    // View binding for this fragment, initialized lazily
    private val binding by lazy {
        FragmentUniversityBinding.inflate(layoutInflater)
    }

    // Navigation controller for navigating between fragments
    private lateinit var navController: NavController

    // ViewModel instance provided by Koin
    private val viewModel: CountryViewModel by inject()

    // Adapter for the RecyclerView, initialized lazily
    private val universityAdapter by lazy {
        UniversityAdapter {
            // Navigate to UniversityDetailFragment with the selected university's data
            try {
                navController.navigate(
                    UniversitiesFragmentDirections.actionUniversityFragmentToUniversityDetailFragment(it)
                )
            } catch (ex: Exception) {
                showLog("${ex.message}")
            }
        }
    }

    // Context and Activity references
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
        // Return the root view from the binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView and query listener
        binding.apply {
            setupRecyclerView()
            listenQuery()
        }

        // Observe ViewModel data
        observeData()

        // Set up result listener for fragment result API
        listenResultListener()
    }

    // Listen for results from other fragments
    private fun listenResultListener() {
        setFragmentResultListener(requestKey) { _, bundle ->
            val result = bundle.getString(countryNameKey)
            result?.let {
                binding.edtQuestion.setText(it)
            }
        }
    }

    // Extension function to setup RecyclerView with adapter
    private fun FragmentUniversityBinding.setupRecyclerView() {
        rvUniversities.apply {
            adapter = universityAdapter
        }
    }

    // Extension function to handle search query input
    private fun FragmentUniversityBinding.listenQuery() {
        binding.search.setOnClickListener {
            val countryName = edtQuestion.text.toString().trim()
            if (countryName.isEmpty()) {
                edtQuestion.error = "Please enter a country name."
            } else {
                val countryExists = Constants.countriesNames.any { it.equals(countryName, ignoreCase = true) }
                if (countryExists) {
                    // Fetch universities for the entered country
                    viewModel.getUniversities(countryName)
                    hideKeyboard()
                } else {
                    mContext?.showToast("Your country name doesn't match.")
                    navController.navigate(UniversitiesFragmentDirections.actionUniversityFragmentToCountryNameDialogFragment())
                }
            }
        }
    }

    // Observe LiveData from ViewModel and handle UI updates
    private fun observeData() {
        viewModel.universityList.observe(viewLifecycleOwner) {
            when (it) {
                is DataStatus.Loading -> {
                    showProgressBar(isShownRV = false, isShownPb = true, isEmptyData = false)
                }

                is DataStatus.Success -> {
                    showProgressBar(isShownRV = true, isShownPb = false, isEmptyData = false)
                    binding.edtQuestion.setText("")
                    it.data?.let { universityList -> universityAdapter.differ.submitList(universityList) }
                }

                is DataStatus.Error -> {
                    showProgressBar(isShownRV = false, isShownPb = false, isEmptyData = true)
                    mContext?.showToastLong(it.error)
                }
            }
        }
    }

    // Update visibility of UI elements based on data status
    private fun showProgressBar(isShownRV: Boolean, isShownPb: Boolean, isEmptyData: Boolean) {
        binding.apply {
            rvUniversities.setVisible(isShownRV)
            pBarLoading.setVisible(isShownPb)
            emptyDataPlaceHolder.setVisible(isEmptyData)
        }
    }

    // Hide the keyboard
    private fun hideKeyboard() {
        val view = mActivity?.currentFocus
        if (view != null) {
            val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
