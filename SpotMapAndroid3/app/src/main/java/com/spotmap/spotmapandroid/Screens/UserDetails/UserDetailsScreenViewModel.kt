package com.spotmap.spotmapandroid.Screens.UserDetails

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.compose.ui.layout.LookaheadScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Class.LoadableResource
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
    USERDETAILS, LOADING, SEPARATOR
}
class UserDetailsScreenViewModel(
    val userHandler: UserHandler,
    val storageService: StorageService,
    val apiService: APIService
) : ViewModel() {

    private val _skaterId = MutableLiveData<String?>()
    val skaterId: LiveData<String?> = _skaterId

    private val _items = MutableLiveData<List<UserDetailsItem>>(listOf(UserDetailsItem.USERDETAILS, UserDetailsItem.SEPARATOR))
    val items: LiveData<List<UserDetailsItem>> = _items

    private val _user = MutableLiveData<LoadableResource<Skater>>(LoadableResource.notLoaded())
    val user: LiveData<LoadableResource<Skater>> = _user

    var isCurrentUserPage: Boolean = false

    init {

        val userId = userHandler.getUserLight()?.id
        if (userId != null) {
            _skaterId.value = userId
        }

        _skaterId.observeForever { id ->
            Log.d("UserDetails", "skaterId updated: $id")
            isCurrentUserPage = (id == null || id == userHandler.getUser()?.id)
            loadUserDetails()
        }
    }

    fun updateSkaterId(id: String) {
        _skaterId.value = id
    }

    fun loadUserDetails() {
        viewModelScope.launch {
            try {
                _skaterId.value?.let {
                    _user.value = LoadableResource.loading()
                    val skaterDetails = apiService.getSkater(it)
                    _user.value = LoadableResource.loaded(skaterDetails)
                }
            } catch (e: Exception) {
                Log.e("UserDetails", "Error loading user details for skaterId: ${_skaterId.value}", e)
                _user.value = LoadableResource.failed(e)
            }
        }
    }

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
                        _user.value = LoadableResource.loaded(apiService.getSkater(user.id))
                        _items.value = listOf(UserDetailsItem.USERDETAILS)
                    }

                } catch (e: Exception) {
                    Log.d("Failed to load", "$e")
                }
            }
        }
    }
}


