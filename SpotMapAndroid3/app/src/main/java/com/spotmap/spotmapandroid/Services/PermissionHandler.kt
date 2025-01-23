package com.spotmap.spotmapandroid.Services

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PermissionHandler() {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var onPermissionGranted: (() -> Unit)? = null
    private var onPermissionDenied: (() -> Unit)? = null
    private var activity: AppCompatActivity? = null

    fun setupPermissionLauncher(activity: AppCompatActivity) {
        this.activity = activity
        if (this.activity != null) {
            permissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    onPermissionGranted?.invoke()
                } else {
                    onPermissionDenied?.invoke()
                }
            }
        }
    }

    fun checkAndRequestPermission(
        permission: String,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, permission) == PackageManager.PERMISSION_GRANTED -> {
                    onGranted()
                }
                it.shouldShowRequestPermissionRationale(permission) -> {
                    onDenied()
                }
                else -> {
                    onPermissionGranted = onGranted
                    onPermissionDenied = onDenied
                    permissionLauncher.launch(permission)
                }
            }
        }
    }

    companion object {
        const val READ_MEDIA_IMAGES = android.Manifest.permission.READ_MEDIA_IMAGES
        const val READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
}