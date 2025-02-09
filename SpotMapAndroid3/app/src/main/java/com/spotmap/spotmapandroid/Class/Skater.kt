package com.spotmap.spotmapandroid.Class

import com.google.firebase.auth.FirebaseUser
import java.util.Date

class Skater(val id: String,
             val userName: String,
             val email: String,
             val photoUrl: String?,
             val creationDate: Date = Date(System.currentTimeMillis())) {

    companion object {
        fun create(user: FirebaseUser): Skater? {

            val name = user.displayName
            val email = user.email
            val photoUrl = if(user.photoUrl != null) { user.photoUrl.toString() } else { null }

            if (name != null
                && email != null) {
                return Skater(id = user.uid,
                    userName = name,
                    email = email,
                    photoUrl = photoUrl)
            }
            return null
        }

        fun create(skaterFeed: SkaterFeed?): Skater? {


            if (skaterFeed?.id != null
                && skaterFeed.userName != null
                && skaterFeed.email != null
                && skaterFeed.photoUrl != null
                && skaterFeed.creationDate != null) {
                return Skater(id = skaterFeed.id,
                    userName = skaterFeed.userName,
                    email = skaterFeed.email,
                    photoUrl = skaterFeed.photoUrl,
                    creationDate = skaterFeed.creationDate)
            }
            return null
        }
    }
}

class SkaterFeed(val id: String? = null,
                 val userName: String? = null,
                 val email: String? = null,
                 val photoUrl: String? = null,
                 val creationDate: Date? = null) {


}