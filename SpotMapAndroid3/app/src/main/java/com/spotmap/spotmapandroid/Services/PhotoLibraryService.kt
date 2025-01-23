package com.spotmap.spotmapandroid.Services

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PhotoLibraryService() {

    private var permissionHandler: PermissionHandler = PermissionHandler()
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private var imageUriCallback: ((Uri) -> Unit)? = null

    fun setupActivity(activity: AppCompatActivity) {
        permissionHandler.setupPermissionLauncher(activity)
        pickImageLauncher = activity.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                Log.d("IMAGE LOAD:", "uri: $uri")
                imageUriCallback?.invoke(uri)
            }
        }
    }

    fun requestImage(
        onDenied: () -> Unit,
        onImageSelected: (Uri) -> Unit
    ) {
        imageUriCallback = onImageSelected
        requestPermission(
            onGranted = { pickImageLauncher.launch("image/*") },
            onDenied = { onDenied() })
    }

    private fun requestPermission(
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        val permission: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionHandler.READ_MEDIA_IMAGES
        } else {
            PermissionHandler.READ_EXTERNAL_STORAGE
        }
        permissionHandler.checkAndRequestPermission(
            permission,
            onGranted = {
                onGranted()
            },
            onDenied = {
                onDenied()
            })
    }
}