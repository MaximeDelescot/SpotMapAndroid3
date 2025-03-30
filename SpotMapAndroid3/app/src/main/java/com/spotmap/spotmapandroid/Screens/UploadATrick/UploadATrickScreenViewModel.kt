package com.spotmap.spotmapandroid.Screens.UploadATrick

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Class.LoadableResource
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.UserHandler
import kotlinx.coroutines.launch

class UploadATrickScreenViewModel(val apiService: APIService, val userHandler: UserHandler): ViewModel() {

    private val _videoUri = MutableLiveData<LoadableResource<Uri?>>()
    val videoUri: LiveData<LoadableResource<Uri?>> = _videoUri


    fun setVideoUri(uri: Uri) {
        _videoUri.value = LoadableResource.loaded<Uri?>(uri)
    }
}
