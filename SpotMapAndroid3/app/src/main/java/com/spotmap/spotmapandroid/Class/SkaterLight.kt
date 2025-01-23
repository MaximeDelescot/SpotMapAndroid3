package com.spotmap.spotmapandroid.Class

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser

class SkaterLight(val id: String,
                  val userName: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",  // id
        parcel.readString() ?: ""   // userName
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userName)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<SkaterLight> {
        override fun createFromParcel(parcel: Parcel): SkaterLight {
            return SkaterLight(parcel)
        }

        override fun newArray(size: Int): Array<SkaterLight?> {
            return arrayOfNulls(size)
        }

        fun create(user: FirebaseUser): SkaterLight? {
            val name = user.displayName
            if (name != null) {
                return SkaterLight(user.uid, name)
            }
            return null
        }

        // Updated factory method to support SkaterLightFeed
        fun create(feed: SkaterLightFeed?): SkaterLight? {
            if (feed?.id != null && feed.userName != null) {
                return SkaterLight(feed.id, feed.userName)
            }
            return null
        }
    }

    class SkaterLightFeed(val id: String? = null,
                          val userName: String? = null) : Parcelable {

        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(userName)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<SkaterLightFeed> {
            override fun createFromParcel(parcel: Parcel): SkaterLightFeed {
                return SkaterLightFeed(parcel)
            }

            override fun newArray(size: Int): Array<SkaterLightFeed?> {
                return arrayOfNulls(size)
            }
        }
    }
}
