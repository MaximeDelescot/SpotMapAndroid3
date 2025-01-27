package com.spotmap.spotmapandroid.Screens.Map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Screens.AddSpot.AddSpotScreenViewModel.AddSpotScreenViewType
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.StorageService
import com.spotmap.spotmapandroid.Services.UserHandler
import kotlinx.coroutines.launch

class MapScreenViewModel (val apiService: APIService): ViewModel() {

    private val _spots = MutableLiveData<List<Spot>>( listOf())
    val spots: LiveData<List<Spot>> = _spots

    fun loadSpots() {
        viewModelScope.launch {
            try {
                _spots.value = apiService.getSpots()
            } catch (e: Exception){

            }
        }
    }
}
