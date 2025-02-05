package com.spotmap.spotmapandroid.Screens.UserDetails

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spotmap.spotmapandroid.Class.Skater
import com.spotmap.spotmapandroid.Commons.Utils.removeTokenParam
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.CompressionType
import com.spotmap.spotmapandroid.Services.StorageService
import com.spotmap.spotmapandroid.Services.UserHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class UserDetailsItem {
    USERDETAILS, LOADING
}

class UserDetailsScreenViewModel(val userHandler: UserHandler, val storageService: StorageService, val apiService: APIService): ViewModel() {

    private val _items = MutableLiveData<List<UserDetailsItem>>(listOf(UserDetailsItem.USERDETAILS))
    val items: LiveData<List<UserDetailsItem>> = _items

    private val _user = MutableLiveData<Skater?>(userHandler.getUser())
    val user: LiveData<Skater?> = _user

    fun saveUserImage(imageView: ImageView) {

        val user = userHandler.getUserLight()
        if (user != null) {
            _items.value = listOf(UserDetailsItem.LOADING)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val url = storageService.save(
                        imageView = imageView,
                        user = user
                    ).removeTokenParam()
                    userHandler.updateUserImage(url)
                    withContext(Dispatchers.Main) {
                        _user.value = userHandler.getUser()
                        _items.value = listOf(UserDetailsItem.USERDETAILS)
                    }

                } catch (e: Exception) {
                    Log.d("Failed to load", "$e")
                }
            }
        }
    }
}


