package com.spotmap.spotmapandroid.Services

import android.util.Log
import androidx.compose.runtime.rememberUpdatedState
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.spotmap.spotmapandroid.Class.Comment
import com.spotmap.spotmapandroid.Class.CommentFeed
import com.spotmap.spotmapandroid.Class.CommentRef
import com.spotmap.spotmapandroid.Class.Skater
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Class.SpotFeed
import com.spotmap.spotmapandroid.Class.SpotRef
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class APICollectionName {
    companion object {
        val spots: String = "spots"
        val skaters: String = "skaters"
        val comments: String = "comments"
        val commentRefs: String = "commentRefs"
    }
}


class APIService(val db: FirebaseFirestore = Firebase.firestore) {

    suspend fun getSpots(): List<Spot> = suspendCancellableCoroutine { continuation ->
        val task = db.collection(APICollectionName.spots)
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

    suspend fun getSpot(id: String): Spot = suspendCancellableCoroutine { continuation ->
        db.collection(APICollectionName.spots)
            .document(id)
            .get()
            .addOnSuccessListener { snapshot ->
                try {
                    val spot = Spot.create(snapshot.toObject<SpotFeed>()) ?: throw Throwable(message = "failed to deserialized spot")
                    continuation.resume(spot)
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

            val spotDocRef = db.collection(APICollectionName.spots)
                .document(spot.id)

            val spotRefDocRef = db.collection(APICollectionName.skaters)
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
            db.collection(APICollectionName.skaters).document(skater.id).set(skater)
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
            db.collection(APICollectionName.skaters).document(skaterId)
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
            .collection(APICollectionName.spots)
            .document(spot.id)
            .collection(APICollectionName.comments)

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

    suspend fun addComment(spot: Spot, comment: Comment) {
        val commentRef = CommentRef(comment.id)
        val batch = db.batch()

        val commentDoc = db.collection(APICollectionName.spots).document(spot.id)
            .collection(APICollectionName.comments).document(comment.id)
        val commentRefDoc = db.collection(APICollectionName.skaters).document(spot.creator.id)
            .collection(APICollectionName.commentRefs).document(commentRef.refId)
        val spotRef = db.collection(APICollectionName.spots).document(spot.id)

        batch.set(commentDoc, comment)
        batch.set(commentRefDoc, commentRef)
        batch.update(spotRef, mapOf("commentCount" to FieldValue.increment(1)))

        batch.commit().await()
    }
}