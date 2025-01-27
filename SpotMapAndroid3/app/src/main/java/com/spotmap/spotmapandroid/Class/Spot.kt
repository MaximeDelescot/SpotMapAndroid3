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
    val description: String,
    spotEnum: SpotType,
    val coordinate: Coordinate,
    var imageUrls: List<String>
) {

    private val type = spotEnum.name

    fun getType(): SpotType {
        return SpotType.valueOf(type)
    }
    companion object {
        fun create(feed: SpotFeed?): Spot? {
            if (feed?.id != null
                && feed.name != null
                && feed.creationDate != null
                && feed.description != null
                && feed.imageUrls != null
                && feed.type != null
            ) {
                val creator = SkaterLight.create(feed.creator)
                val coordinate = Coordinate.create(feed.coordinate)

                if (creator != null && coordinate != null) {
                    return Spot(
                        id = feed.id,
                        name = feed.name,
                        creator = creator,
                        description = feed.description,
                        coordinate = coordinate,
                        spotEnum = SpotType.valueOf(feed.type),
                        imageUrls = feed.imageUrls
                    )
                }
            }
            return null
        }
    }
}


// SpotFeed class should also implement Parcelable
class SpotFeed(
    val id: String? = null,
    val creationDate: Date? = null,
    val creator: SkaterLight.SkaterLightFeed? = null,
    val name: String? = null,
    val description: String? = null,
    val type: String? = null,
    val coordinate: Coordinate.CoordinateFeed? = null,
    val imageUrls: List<String>? = null
) {


}
