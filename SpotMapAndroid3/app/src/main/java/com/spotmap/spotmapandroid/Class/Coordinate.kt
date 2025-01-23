package com.spotmap.spotmapandroid.Class

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng


class Coordinate(val latitude: Double,
                 val longitude: Double) : Parcelable {

    fun getLatLong(): LatLng {
        return LatLng(latitude, longitude)
    }

    constructor(parcel: Parcel) : this(
        parcel.readDouble(), // Read latitude
        parcel.readDouble()  // Read longitude
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Coordinate> {
        override fun createFromParcel(parcel: Parcel): Coordinate {
            return Coordinate(parcel)
        }

        override fun newArray(size: Int): Array<Coordinate?> {
            return arrayOfNulls(size)
        }

        fun create(feed: CoordinateFeed?): Coordinate? {
            if (feed?.latitude != null && feed.longitude != null) {
                return Coordinate(
                    latitude = feed.latitude,
                    longitude = feed.longitude
                )
            }
            return null
        }
    }

    class CoordinateFeed(
        val latitude: Double? = null,
        val longitude: Double? = null
    ) : Parcelable {

        constructor(parcel: Parcel) : this(
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(latitude)
            parcel.writeValue(longitude)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<CoordinateFeed> {
            override fun createFromParcel(parcel: Parcel): CoordinateFeed {
                return CoordinateFeed(parcel)
            }

            override fun newArray(size: Int): Array<CoordinateFeed?> {
                return arrayOfNulls(size)
            }
        }
    }
}
