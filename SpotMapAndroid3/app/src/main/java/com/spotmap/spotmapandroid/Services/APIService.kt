package com.spotmap.spotmapandroid.Services

import android.util.Log
import androidx.compose.runtime.rememberUpdatedState
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import com.spotmap.spotmapandroid.Class.Comment
import com.spotmap.spotmapandroid.Class.CommentFeed
import com.spotmap.spotmapandroid.Class.Skater
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Class.SpotFeed
import com.spotmap.spotmapandroid.Class.SpotRef
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class APIService(val db: FirebaseFirestore = Firebase.firestore) {

    suspend fun getSpots(): List<Spot> = suspendCancellableCoroutine { continuation ->
        val task = db.collection("spots")
            .get()
            .addOnSuccessListener { result ->
                try {
                    val spots = result.toObjects<SpotFeed>().mapNotNull { Spot.create(it) }
                    continuation.resume(spots)
                } catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    suspend fun addSpot(spot: Spot) {

        suspendCancellableCoroutine<Unit> { continuation ->

            val spotRef = SpotRef(spot.id)

            val spotDocRef = db.collection("spots")
                .document(spot.id)

            val spotRefDocRef = db.collection("skaters")
                .document(spot.creator.id)
                .collection("spotRefs")
                .document(spotRef.refId)

            val batch = db.batch()

            batch.set(spotDocRef, spot)
            batch.set(spotRefDocRef, spotRef)

            batch.commit()
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }
        }
    }

    suspend fun createSkater(skater: Skater)  {
        suspendCancellableCoroutine<Unit> { continuation ->
            db.collection("skaters").document(skater.id).set(skater)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }
        }
    }

    suspend fun updateSkater(skaterId: String, url: String) {
        suspendCoroutine<Unit> { continuation ->
            db.collection("skaters").document(skaterId)
                .update(mapOf("photoUrl" to url))
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }
        }
    }

    suspend fun getComments(spot: Spot, numberMax: Int? = null): List<Comment> = suspendCancellableCoroutine { continuation ->
        val commentsRef = FirebaseFirestore.getInstance()
            .collection("spots")
            .document(spot.id)
            .collection("comments")

        var query = commentsRef
            .orderBy("creationDate", Query.Direction.DESCENDING)

        numberMax?.let {
            query = query.limit(it.toLong())
        }

        val snapshot = query.get().addOnSuccessListener { result ->
            try {
                val comments = result.toObjects<CommentFeed>().mapNotNull { Comment.create(it) }
                continuation.resume(comments)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}