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
import com.spotmap.spotmapandroid.Class.Publication
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.UserHandler
import kotlinx.coroutines.launch

enum class SpotDetailsItem {
    SPOTDETAILS,
    COMMENT,
    PUBLICATION,
    LOADING
}

class SpotDetailsScreenViewModel(val apiService: APIService, val userHandler: UserHandler): ViewModel() {

    private val _spot = MutableLiveData<LoadableResource<Spot>>()
    val spot: LiveData<LoadableResource<Spot>> = _spot

    private val _comments = MediatorLiveData<LoadableResource<List<Comment>>>()
    val comments: LiveData<LoadableResource<List<Comment>>> = _comments

    private val _publications = MediatorLiveData<LoadableResource<List<Publication>>>()
    val publications: LiveData<LoadableResource<List<Publication>>> = _publications

    private val _items = MediatorLiveData<List<SpotDetailsItem>>()
    val items: LiveData<List<SpotDetailsItem>> = _items

    init {
        _items.addSource(_spot) { spot ->
            val comments = _comments.value
            val publications = _publications.value
            if (comments != null && publications != null) {
                updateItems(spot, comments, publications)
            }
        }

        _items.addSource(_comments) { comments ->
            val spot = _spot.value
            val publications = _publications.value
            if (spot != null && publications != null) {
                updateItems(spot, comments, publications)
            }
        }

        _items.addSource(_publications) { publications ->
            val spot = _spot.value
            val comments = _comments.value
            if (spot != null && comments != null) {
                updateItems(spot, comments, publications)
            }
        }
    }

    fun sendComment(comment: String) {
        _comments.value = LoadableResource.loading()
        viewModelScope.launch {
            try {
                _comments.value = LoadableResource.loading()
                val creator = userHandler.getUserLight()
                val spot = _spot.value?.resource
                if (spot != null && creator != null) {
                    val comment = Comment(content = comment, creator = creator)
                    apiService.addComment(
                        spot = spot,
                        comment = comment)
                    loadSpot()
                    loadComments()
                    loadPublications()
                }
            } catch (e: Exception) {
                Log.d("Failed to add comment:", "$e")
            }

        }
    }

    fun setSpot(spot: Spot) {
        _spot.value = LoadableResource.loaded<Spot>(spot)
        viewModelScope.launch {
            loadComments()
            loadPublications()
        }
    }

    private fun updateItems(spot: LoadableResource<Spot>,
                            comments: LoadableResource<List<Comment>>,
                            publications: LoadableResource<List<Publication>>) {
        val newItems = generateItems(spot, comments, publications)
        _items.value = newItems
    }

    private fun generateItems(spot: LoadableResource<Spot>,
                              comments: LoadableResource<List<Comment>>,
                              publications: LoadableResource<List<Publication>>): List<SpotDetailsItem> {
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

        if (publications.status == LoadableResourceType.LOADED) {
            list = list + listOf(SpotDetailsItem.PUBLICATION)
        } else if (comments.status == LoadableResourceType.LOADING) {
            list = list + listOf(SpotDetailsItem.LOADING)
        }

        return list
    }

    private suspend fun loadSpot() {
        _spot.value?.resource?.let {
            viewModelScope.launch {
                try {
                    val result = apiService.getSpot(it.id)
                    _spot.value = LoadableResource.loaded<Spot>(result)
                } catch (e: Exception){
                    Log.d("Failed to load comments:", "$e")
                }
            }
        }
    }

    private suspend fun loadComments() {
        _comments.value = LoadableResource.loading()
        _spot.value?.resource?.let {
                try {
                    val result = apiService.getComments(spot = it, numberMax = 2)
                    _comments.value = LoadableResource.loaded<List<Comment>>(result)
                } catch (e: Exception){
                    Log.d("Failed to load comments:", "$e")
                }
        }
    }

    private suspend fun loadPublications() {
        _publications.value = LoadableResource.loading()
        _spot.value?.resource?.let {
            try {
                val result = apiService.getPublications(spot = it)
                _publications.value = LoadableResource.loaded<List<Publication>>(result)
            } catch (e: Exception){
                Log.d("Failed to load comments:", "$e")
            }
        }
    }
}
