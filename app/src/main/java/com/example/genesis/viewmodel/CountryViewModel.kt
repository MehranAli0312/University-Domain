package com.example.genesis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genesis.models.UniversityModel
import com.example.genesis.repositories.ApiRepository
import com.example.genesis.utils.DataStatus
import kotlinx.coroutines.launch


class CountryViewModel(private val repository: ApiRepository) : ViewModel() {

    // MutableLiveData to hold the status of the university list data
    private val _universityList = MutableLiveData<DataStatus<UniversityModel>>()

    // Public LiveData to observe the university list data
    val universityList: LiveData<DataStatus<UniversityModel>>
        get() = _universityList

    // Function to fetch universities based on the search query
    fun getUniversities(searchQuery: String) = viewModelScope.launch {
        // Collect the flow from the repository and update the LiveData
        repository.getUniversities(searchQuery).collect { dataStatus ->
            _universityList.value = dataStatus
        }
    }
}
