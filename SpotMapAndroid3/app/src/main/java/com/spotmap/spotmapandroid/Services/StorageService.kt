package com.spotmap.spotmapandroid.Services

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.spotmap.spotmapandroid.Class.Spot
import java.io.ByteArrayOutputStream
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StorageService() {

    val storage = Firebase.storage

    interface UploadCallback {
        fun onSuccess(downloadUrl: String)
        fun onFailure()
    }

    suspend fun save(imageView: ImageView, spot: Spot): String {
        return suspendCoroutine { continuation ->
            val storageRef = storage.reference
            val id = UUID.randomUUID().toString()
            val spaceRef = storageRef.child("Spots/${spot.id}/$id.jpg")

            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 35, baos)
            val data = baos.toByteArray()

            val uploadTask = spaceRef.putBytes(data)

            uploadTask.addOnFailureListener {
                continuation.resumeWithException(Throwable("Failed to upload image"))
            }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    continuation.resume(uri.toString()) // Success result
                }
            }
        }
    }

}