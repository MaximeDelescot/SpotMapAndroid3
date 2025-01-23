package com.spotmap.spotmapandroid.Services

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.spotmap.spotmapandroid.Class.Spot
import java.io.ByteArrayOutputStream

class StorageService() {

    val storage = Firebase.storage

    interface UploadCallback {
        fun onSuccess(downloadUrl: String)
        fun onFailure()
    }

    fun save(imageView: ImageView, spot: Spot, callback: UploadCallback) {

        var storageRef = storage.reference
        var spaceRef = storageRef.child("Spots/${spot.id}/1.jpg")

//        imageView.isDrawingCacheEnabled = true
//        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 35, baos)
        val data = baos.toByteArray()

        val uploadTask = spaceRef.putBytes(data)

        uploadTask.addOnFailureListener {
            Log.d("STORAGE", "failed")
            callback.onFailure()
        }.addOnSuccessListener { taskSnapshot ->
            Log.d("STORAGE", "succeed")
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                callback.onSuccess(uri.toString())
            }
        }
    }
}