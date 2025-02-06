package com.spotmap.spotmapandroid.Screens.SpotDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Class.Comment
import com.spotmap.spotmapandroid.Class.LoadableResource
import com.spotmap.spotmapandroid.Class.LoadableResourceType
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Services.APIService
import kotlinx.coroutines.launch

enum class SpotDetailsItem {
    SPOTDETAILS,
    COMMENT,
    LOADING
}

class SpotDetailsScreenViewModel(val apiService: APIService): ViewModel() {

    private val _spot = MutableLiveData<LoadableResource<Spot>>()
    val spot: LiveData<LoadableResource<Spot>> = _spot

    private val _comments = MediatorLiveData<LoadableResource<List<Comment>>>()
    val comments: LiveData<LoadableResource<List<Comment>>> = _comments

    private val _items = MediatorLiveData<List<SpotDetailsItem>>()
    val items: LiveData<List<SpotDetailsItem>> = _items

    init {
        _items.addSource(_spot) { spot ->
            val comments = _comments.value
            if (comments != null) {
                updateItems(spot, comments)

            }
        }

        _items.addSource(_comments) { comments ->
            val spot = _spot.value
            if (spot != null) {
                updateItems(spot, comments)
            }
        }
    }

    fun setSpot(spot: Spot) {
        _spot.value = LoadableResource.loaded<Spot>(spot)
        loadComments()
    }

    private fun updateItems(spot: LoadableResource<Spot>, comments: LoadableResource<List<Comment>>) {
        val newItems = generateItems(spot, comments)
        _items.value = newItems
    }

    private fun generateItems(spot: LoadableResource<Spot>, comments: LoadableResource<List<Comment>>): List<SpotDetailsItem> {
        var list = listOf<SpotDetailsItem>()

        if (spot.status == LoadableResourceType.LOADED) {
            list = list + listOf(SpotDetailsItem.SPOTDETAILS)
        } else if (spot.status == LoadableResourceType.LOADING) {
            list = list + listOf(SpotDetailsItem.LOADING)
        }

        if (comments.status == LoadableResourceType.LOADED) {
            list = list + listOf(SpotDetailsItem.COMMENT)
        } else if (comments.status == LoadableResourceType.LOADING) {
            list = list + listOf(SpotDetailsItem.LOADING)
        }

        return list
    }

    private fun loadComments() {

        _spot.value?.resource?.let {
            viewModelScope.launch {
                try {
                    val result = apiService.getComments(spot = it, numberMax = 2)
                    _comments.value = LoadableResource.loaded<List<Comment>>(result)
                } catch (e: Exception){
                    Log.d("Failed to load comments:", "$e")
                }
            }
        }
    }
}
