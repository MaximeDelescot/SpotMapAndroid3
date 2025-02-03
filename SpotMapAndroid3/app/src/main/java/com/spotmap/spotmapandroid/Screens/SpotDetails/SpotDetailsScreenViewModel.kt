package com.spotmap.spotmapandroid.Screens.SpotDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Services.APIService
import kotlinx.coroutines.launch

enum class SpotDetailsItem {
    SPOTDETAILS,
    COMMENTS
}


class SpotDetailsScreenViewModel(val apiService: APIService): ViewModel() {

    private val _spot = MutableLiveData<Spot>()
    val spot: LiveData<Spot> = _spot

    private val _items = MutableLiveData<List<SpotDetailsItem>>()
    val items: LiveData<List<SpotDetailsItem>> = _items

    fun setSpot(spot: Spot) {
        _spot.value = spot
        _items.value = listOf( SpotDetailsItem.SPOTDETAILS, SpotDetailsItem.COMMENTS)
    }
}
