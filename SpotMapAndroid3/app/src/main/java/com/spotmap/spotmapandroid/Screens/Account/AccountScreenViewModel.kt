package com.spotmap.spotmapandroid.Screens.Account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Services.UserHandler
import kotlinx.coroutines.launch

class AccountScreenViewModel (val userHandler: UserHandler): ViewModel() {

    enum class DisplayedView {
        SIGNUP,
        SIGNIN,
        LOADING,
        LOGGED
    }

    val initialSelectedView = if (userHandler.getUser() == null) { DisplayedView.SIGNUP } else { DisplayedView.LOGGED }
    private val _displayedView = MutableLiveData<DisplayedView>(initialSelectedView)
    val displayedView: LiveData<DisplayedView> get() = _displayedView

    fun setDisplayedView(displayedView: DisplayedView) {
        _displayedView.value = displayedView
    }
    fun tryToSignUp(email: String, password: String, username: String) {
        _displayedView.value = DisplayedView.LOADING
        viewModelScope.launch {
            try {
                userHandler.createAccount(email, password, username)
                _displayedView.value = DisplayedView.LOGGED
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun tryToSignIn(email: String, password: String) {
        _displayedView.value = DisplayedView.LOADING
        viewModelScope.launch {
            try {
                userHandler.login(email, password)
                _displayedView.value = DisplayedView.LOGGED
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}