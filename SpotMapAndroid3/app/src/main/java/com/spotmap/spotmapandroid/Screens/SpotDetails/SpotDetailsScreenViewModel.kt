package com.spotmap.spotmapandroid.Screens.SpotDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Services.APIService
import kotlinx.coroutines.launch

class SpotDetailsScreenViewModel(val apiService: APIService): ViewModel() {

    private val _selectedSpot = MutableLiveData<Spot?>()
    val selectedSpot: LiveData<Spot?> = _selectedSpot

    fun setSpot(spot: Spot) {
        _selectedSpot.value = spot
    }
}