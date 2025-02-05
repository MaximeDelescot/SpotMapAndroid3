package com.spotmap.spotmapandroid.Screens.SpotDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Class.Comment
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

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments

    private val _items = MutableLiveData<List<SpotDetailsItem>>()
    val items: LiveData<List<SpotDetailsItem>> = _items

    fun setSpot(spot: Spot) {
        _spot.value = spot
        _items.value = listOf( SpotDetailsItem.SPOTDETAILS, SpotDetailsItem.COMMENTS)
        loadSpots()
    }

    fun loadSpots() {
        spot.value?.let {
            viewModelScope.launch {
                try {
                    _comments.value = apiService.getComments(spot = it)
                } catch (e: Exception){
                    Log.d("Failed to load comments:", "$e")
                }
            }
        }
    }
}
