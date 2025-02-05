package com.spotmap.spotmapandroid.Screens.SpotDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Class.Comment
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Services.APIService
import kotlinx.coroutines.launch

enum class SpotDetailsItem {
    SPOTDETAILS,
    COMMENT
}

class SpotDetailsScreenViewModel(val apiService: APIService): ViewModel() {

    private val _spot = MutableLiveData<Spot>()
    val spot: LiveData<Spot> = _spot

    private val _comments = MediatorLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments

    private val _items = MediatorLiveData<List<SpotDetailsItem>>()
    val items: LiveData<List<SpotDetailsItem>> = _items

    init {
        _items.addSource(_spot) { spot ->
            updateItems(spot, _comments.value)
        }

        _items.addSource(_comments) { comments ->
            updateItems(_spot.value, comments)
        }
    }

    fun setSpot(spot: Spot) {
        _spot.value = spot
        loadSpots()
    }

    private fun updateItems(spot: Spot?, comments: List<Comment>?) {
        if (spot != null && comments != null) {
            val newItems = generateItems(spot, comments)
            _items.value = newItems
        }
    }

    private fun generateItems(spot: Spot, comments: List<Comment>): List<SpotDetailsItem> {
        var list = listOf<SpotDetailsItem>(SpotDetailsItem.SPOTDETAILS, SpotDetailsItem.COMMENT)
        return list
    }

    private fun loadSpots() {
        spot.value?.let {
            viewModelScope.launch {
                try {
                    _comments.value = apiService.getComments(spot = it, numberMax = 2)
                } catch (e: Exception){
                    Log.d("Failed to load comments:", "$e")
                }
            }
        }
    }
}
