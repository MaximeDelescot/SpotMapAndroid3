package com.spotmap.spotmapandroid.Screens.UserDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spotmap.spotmapandroid.Class.Skater
import com.spotmap.spotmapandroid.Services.UserHandler

enum class UserDetailsItem {
    USERDETAILS
}

class UserDetailsScreenViewModel(val userHandler: UserHandler): ViewModel() {

    private val _items = MutableLiveData<List<UserDetailsItem>>(listOf(UserDetailsItem.USERDETAILS))
    val items: LiveData<List<UserDetailsItem>> = _items

    private val _user = MutableLiveData<Skater?>(userHandler.getUser())
    val user: LiveData<Skater?> = _user
}