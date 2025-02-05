package com.spotmap.spotmapandroid.Class

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser

class SkaterLight(val id: String,
                  val userName: String,
                  val photoUrl: String?)  {

    companion object {
        fun create(user: FirebaseUser): SkaterLight? {
            val name = user.displayName
            if (name != null) {
                return SkaterLight(
                    user.uid,
                    name,
                    photoUrl = if (user.photoUrl != null) {
                        user.photoUrl.toString()
                    } else {
                        null
                    }
                )
            }
            return null
        }

        fun create(feed: SkaterLightFeed?): SkaterLight? {
            if (feed?.id != null && feed.userName != null) {
                return SkaterLight(feed.id, feed.userName, feed.photoUrl)
            }
            return null
        }
    }
}

class SkaterLightFeed(val id: String? = null,
                      val userName: String? = null,
                      val photoUrl: String? = null) {


}

