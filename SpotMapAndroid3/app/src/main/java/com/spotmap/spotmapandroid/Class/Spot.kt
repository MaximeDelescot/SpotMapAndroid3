package com.spotmap.spotmapandroid.Class

import android.os.Parcel
import android.os.Parcelable
import java.util.Date
import java.util.UUID


enum class SpotType {
    Street,
    Park
}

class SpotRef(val refId: String) {
}

class Spot(
    val id: String = UUID.randomUUID().toString(),
    val creationDate: Date = Date(System.currentTimeMillis()),
    val creator: SkaterLight,
    val name: String,
    val address: String,
    spotEnum: SpotType,
    val coordinate: Coordinate,
    var imageUrl: String
) : Parcelable {

    private val type = spotEnum.name

    fun getType(): SpotType {
        return SpotType.valueOf(type)
    }

    // Constructor for Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: UUID.randomUUID().toString(), // id
        Date(parcel.readLong()), // creationDate
        parcel.readParcelable<SkaterLight>(SkaterLight::class.java.classLoader)!!, // creator
        parcel.readString() ?: "", // name
        parcel.readString() ?: "", // address
        SpotType.valueOf(parcel.readString() ?: SpotType.Street.name), // spotEnum
        parcel.readParcelable<Coordinate>(Coordinate::class.java.classLoader)!!, // coordinate
        parcel.readString() ?: "" // imageUrl
    )

    // Method to write the object to a Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeLong(creationDate.time)
        parcel.writeParcelable(creator, flags)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(type)
        parcel.writeParcelable(coordinate, flags)
        parcel.writeString(imageUrl)
    }

    // Describe the contents
    override fun describeContents(): Int = 0

    // Companion object for Parcelable.Creator
    companion object CREATOR : Parcelable.Creator<Spot> {
        override fun createFromParcel(parcel: Parcel): Spot {
            return Spot(parcel)
        }

        override fun newArray(size: Int): Array<Spot?> {
            return arrayOfNulls(size)
        }

        // Factory method to create a Spot from a SpotFeed
        fun create(feed: SpotFeed?): Spot? {
            if (feed?.id != null
                && feed.name != null
                && feed.creationDate != null
                && feed.address != null
                && feed.imageUrl != null
                && feed.type != null
            ) {
                val creator = SkaterLight.create(feed.creator)
                val coordinate = Coordinate.create(feed.coordinate)

                if (creator != null && coordinate != null) {
                    return Spot(
                        id = feed.id,
                        name = feed.name,
                        creator = creator,
                        address = feed.address,
                        coordinate = coordinate,
                        spotEnum = SpotType.valueOf(feed.type),
                        imageUrl = feed.imageUrl
                    )
                }
            }
            return null
        }
    }

    // SpotFeed class should also implement Parcelable
    class SpotFeed(
        val id: String? = null,
        val creationDate: Date? = null,
        val creator: SkaterLight.SkaterLightFeed? = null,
        val name: String? = null,
        val address: String? = null,
        val type: String? = null,
        val coordinate: Coordinate.CoordinateFeed? = null,
        val imageUrl: String? = null
    ) : Parcelable {

        // Constructor for Parcelable
        constructor(parcel: Parcel) : this(
            parcel.readString(), // id
            Date(parcel.readLong()), // creationDate
            parcel.readParcelable<SkaterLight.SkaterLightFeed>(SkaterLight.SkaterLightFeed::class.java.classLoader),
            parcel.readString(), // name
            parcel.readString(), // address
            parcel.readString(), // type
            parcel.readParcelable<Coordinate.CoordinateFeed>(Coordinate.CoordinateFeed::class.java.classLoader),
            parcel.readString() // imageUrl
        )

        // Write the properties to the parcel
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeLong(creationDate?.time ?: 0L)
            parcel.writeParcelable(creator, flags)
            parcel.writeString(name)
            parcel.writeString(address)
            parcel.writeString(type)
            parcel.writeParcelable(coordinate, flags)
            parcel.writeString(imageUrl)
        }

        // Describe the contents
        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<SpotFeed> {
            override fun createFromParcel(parcel: Parcel): SpotFeed {
                return SpotFeed(parcel)
            }

            override fun newArray(size: Int): Array<SpotFeed?> {
                return arrayOfNulls(size)
            }
        }
    }
}
