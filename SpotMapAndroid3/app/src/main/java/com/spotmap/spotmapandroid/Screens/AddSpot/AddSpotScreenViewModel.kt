package com.spotmap.spotmapandroid.Screens.AddSpot

import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.UserHandler
import kotlin.properties.Delegates.observable

class AddSpotScreenViewModel (val userHandler: UserHandler,
                              val apiService: APIService): ViewModel() {

    enum class AddSpotScreenViewType { `NOT-LOGGED`, `ADD-FORM` }

    private val _viewToDisplay = MutableLiveData(getViewToDisplay())
    val viewToDisplay: LiveData<AddSpotScreenViewType> = _viewToDisplay

    init {
        userHandler.userDidUpdate.observeForever(Observer {
            _viewToDisplay.value = getViewToDisplay()
        })
    }

    private fun getViewToDisplay(): AddSpotScreenViewType {
        if(userHandler.getUser() == null) {
            return AddSpotScreenViewType.`NOT-LOGGED`
        } else {
            return AddSpotScreenViewType.`ADD-FORM`
        }
    }

    fun createSpot(name: String, description: String) {

        Log.d("CREATE SPOT:", "$name")
    }
}
