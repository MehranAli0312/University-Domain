package com.example.genesis.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UniversityModelItem(
    @SerializedName("country") val countryName: String,
    @SerializedName("name") val universityName: String,
    @SerializedName("alpha_two_code") val countryCode: String,
    @SerializedName("state-province") val stateProvince: String? = "NA",
    @SerializedName("domains") val universityDomains: List<String>,
    @SerializedName("web_pages") val webPages: List<String>,
    var expand: Boolean = false
) : Parcelable