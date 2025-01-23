package com.spotmap.spotmapandroid.Class

import com.google.firebase.auth.FirebaseUser
import java.util.Date

class Skater(val id: String,
             val userName: String,
             val email: String,
             val creationDate: Date = Date(System.currentTimeMillis())) {

    companion object {
        fun create(user: FirebaseUser): Skater? {

            val name = user.displayName
            val email = user.email

            if (name != null && email != null) {
                return Skater(user.uid, name, email)
            }
            return null
        }
    }
}