package com.spotmap.spotmapandroid.Services

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.spotmap.spotmapandroid.Class.Spot
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.min

class CompressionType(val maxWidth: Int, val compression: Int, val nameSuffix: String) {

    companion object {
        fun verySmall(): CompressionType {
            return CompressionType(
                maxWidth = 270,
                compression = 35,
                nameSuffix = "-small")
        }

        fun normal(): CompressionType {
            return CompressionType(
                maxWidth = 720,
                compression = 35,
                nameSuffix = "-normal")
        }

        fun userImage(): CompressionType {
            return CompressionType(
                maxWidth = 180,
                compression = 35,
                nameSuffix = "")
        }
    }

    }

class StorageService() {

    val storage = Firebase.storage

    interface UploadCallback {
        fun onSuccess(downloadUrl: String)
        fun onFailure()
    }

    suspend fun save(imageView: ImageView, spot: Spot, compressionType: CompressionType): String {
        return suspendCoroutine { continuation ->

            val storageRef = storage.reference
            val id = UUID.randomUUID().toString()
            val spaceRef = storageRef.child("Spots/${spot.id}/${id}${compressionType.nameSuffix}.jpg")

            val imageResized = resizeImageIfNeeded(
                imageView = imageView,
                maxDimension = compressionType.maxWidth)

            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressionType.compression, baos)
            val data = baos.toByteArray()

            val uploadTask = spaceRef.putBytes(data)

            uploadTask.addOnFailureListener {
                continuation.resumeWithException(Throwable("Failed to upload image"))
            }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    continuation.resume(uri.toString())
                }
            }
        }
    }

    suspend fun deleteSpotFolder(spot: Spot) {

        val storageRef = storage.reference
        val spaceRef = storageRef.child("Spots/${spot.id}")

        try {
            val result = spaceRef.listAll().await()

            for (item in result.items) {
                try {
                    item.delete().await()
                } catch (e: Exception) {
                    throw e
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun resizeImageIfNeeded(imageView: ImageView, maxDimension: Int): Bitmap? {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap

        val width = bitmap.width
        val height = bitmap.height

        if (width <= maxDimension && height <= maxDimension) {
            return bitmap
        }
        val scale = min(maxDimension.toFloat() / width, maxDimension.toFloat() / height)

        val newWidth = (width * scale).toInt()
        val newHeight = (height * scale).toInt()

        val resizedBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resizedBitmap)
        val rect = Rect(0, 0, newWidth, newHeight)

        canvas.drawBitmap(bitmap, null, rect, null)

        return resizedBitmap
    }
}