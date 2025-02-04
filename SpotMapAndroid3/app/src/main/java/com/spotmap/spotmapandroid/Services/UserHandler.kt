package com.spotmap.spotmapandroid.Services

import android.net.Uri
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.userProfileChangeRequest
import com.spotmap.spotmapandroid.Class.Skater
import com.spotmap.spotmapandroid.Class.SkaterLight
import com.spotmap.spotmapandroid.Screens.Account.AccountScreenViewModel.DisplayedView
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resumeWithException

class UserHandler(val apiService: APIService) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userDidUpdate = MutableLiveData<Unit>()
    val userDidUpdate: LiveData<Unit> get() = _userDidUpdate

    private val authStateListener = FirebaseAuth.AuthStateListener {
        _userDidUpdate.value = Unit
    }

    init {
        auth.addAuthStateListener(authStateListener)
    }

    fun getUser(): Skater? {
        val user = auth.currentUser
        if (user != null) {
            return Skater.create(user)
        } else {
            return null
        }
    }

    fun getUserLight(): SkaterLight? {
        val user = auth.currentUser
        if (user != null) {
            return SkaterLight.create(user)
        } else {
            return null
        }
    }

    fun logOut() {
        auth.signOut()
    }

    suspend fun login(email: String, password: String) {
        return suspendCancellableCoroutine<Unit> { cont ->
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(Unit, onCancellation = null)
                    } else {
                        cont.resumeWithException(task.exception ?: Exception("Unknown error"))
                    }
                }
        }
    }

    suspend fun createAccount(email: String, password: String, userName: String) {

        val user = createUser(auth, email, password)
        try {
            setUserProfile(user, userName)
            val skater = Skater.create(user)
            if (skater != null) {
                apiService.createSkater(skater)
            } else {
                throw Exception("Failed to save skater in database")
            }
        } catch (e: Exception) {
            deleteUser(user)
            throw e
        }
    }

    private suspend fun createUser(
        auth: FirebaseAuth,
        email: String,
        password: String
    ): FirebaseUser {
        return suspendCancellableCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    val user = auth.currentUser
                    if (task.isSuccessful && user != null) {
                        continuation.resume(user, onCancellation = null)
                    } else {
                        continuation.resumeWithException(
                            task.exception ?: Exception("Unknown error")
                        )
                    }
                }
        }
    }

    private suspend fun setUserProfile(user: FirebaseUser, userName: String) {
        suspendCancellableCoroutine<Unit> { continuation ->
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build()

            user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Unit, onCancellation = null)
                    } else {
                        continuation.resumeWithException(
                            task.exception ?: Exception("Failed to update profile")
                        )
                    }
                }
        }
    }

    private suspend fun deleteUser(user: FirebaseUser) {
        suspendCancellableCoroutine<Unit> { continuation ->
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Unit, onCancellation = null)
                    } else {
                        continuation.resumeWithException(
                            task.exception ?: Exception("Failed to delete user")
                        )
                    }
                }
        }
    }

    suspend fun updateUserImage(url: String) {
        val id = getUserLight()?.id ?: return

        val user = FirebaseAuth.getInstance().currentUser
        val changeRequest = user?.updateProfile(
            userProfileChangeRequest {
                photoUri = Uri.parse(url)
            }
        )

        try {
            changeRequest?.await()
            apiService.updateSkater(id, url)
        } catch (e: Exception) {
            throw e
        }
    }
}