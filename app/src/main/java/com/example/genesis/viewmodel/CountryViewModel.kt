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


    private val _universityList = MutableLiveData<DataStatus<UniversityModel>>()
    val universityList: LiveData<DataStatus<UniversityModel>>
        get() = _universityList


    fun getUniversities(searchQuery: String) = viewModelScope.launch {
        repository.getUniversities(searchQuery).collect {
            _universityList.value = it
        }
    }


}