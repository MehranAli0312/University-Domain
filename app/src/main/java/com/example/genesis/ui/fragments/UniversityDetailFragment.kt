package com.example.genesis.ui.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.genesis.databinding.FragmentUniversityDetailBinding
import com.example.genesis.models.UniversityModelItem
import com.example.genesis.utils.LatLngGeocoder
import com.example.genesis.utils.showToast
import kotlinx.coroutines.launch

class UniversityDetailFragment : Fragment() {

    private val binding by lazy { FragmentUniversityDetailBinding.inflate(layoutInflater) }

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getArgData()
        binding.clickListen()
    }

    @SuppressLint("SetTextI18n")
    private fun FragmentUniversityDetailBinding.getArgData() {
        // Safely retrieve the arguments and cast them
        val args = arguments?.let {
            UniversityDetailFragmentArgs.fromBundle(it)
        }

        args?.universityModel?.let { item ->
            // Use the universityModel object
            universityName.text = "University Name: ${item.universityName}"
            universityDomains.text = "Domains: ${item.universityDomains[0]}"
            countryCode.text = "Country Code: ${item.countryCode}"
            webPages.text = "Web Sites: ${item.webPages[0]}"
            stateProvince.text = "State / Province: ${item.stateProvince}"
            countryName.text = "Country: ${item.countryName}"
        }
    }

    private fun FragmentUniversityDetailBinding.clickListen() {
        openMapView.setOnClickListener {
            val name = universityName.text.toString()
            if (name.isNotEmpty()) {
                lifecycleScope.launch {
                    val latLngResult =
                        LatLngGeocoder.getLatLngFromLocationName(mContext, name)
                    latLngResult?.let {
                        openMap(it.latitude, it.longitude, name)
                    } ?: run {
                        mContext.showToast("Location not found")
                    }
                }
            } else {
                mContext.showToast("you have no destination...!")
            }
        }

        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }


    private fun openMap(lat: Double, lng: Double, label: String) {
        try {
            val uri = Uri.parse("geo:$lat,$lng?q=$lat,$lng($label)")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.google.android.apps.maps")
            }
            startActivity(intent)
        } catch (e: Exception) {
            val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lng")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(webIntent)
        }
    }
}