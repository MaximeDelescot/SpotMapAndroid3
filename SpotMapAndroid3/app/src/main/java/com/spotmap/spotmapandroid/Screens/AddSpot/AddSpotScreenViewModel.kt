package com.spotmap.spotmapandroid.Screens.AddSpot

import android.util.Log
import androidx.lifecycle.ViewModel
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.UserHandler

class AddSpotScreenViewModel (val userHandler: UserHandler,
                              val apiService: APIService): ViewModel() {

    fun createSpot(name: String, description: String) {

        Log.d("CREATE SPOT:", "$name")
    }
}
