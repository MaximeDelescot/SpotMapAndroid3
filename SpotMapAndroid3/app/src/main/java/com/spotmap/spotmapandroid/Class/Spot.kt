package com.spotmap.spotmapandroid.Class

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.Date
import java.util.UUID
import kotlin.Boolean


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
    val commentCount: Int,
    var normalImageUrls: List<String>,
    var smallImageUrls: List<String>,
    var needToPay: Boolean,
    var shelteredFromRain: Boolean,
    var hasFixedHours: Boolean,
    var hasLighting: Boolean
) {

    private val type = spotEnum.name

    fun getType(): SpotType {
        return SpotType.valueOf(type)
    }

    @Exclude
    fun getInfosText(): String {
        val strings = mutableListOf<String?>()

        if (needToPay == true) strings.add("Not free")
        if (shelteredFromRain == true) strings.add("Sheltered")
        if (hasFixedHours == true) strings.add("Fixed hours")
        if (hasLighting == true) strings.add("Enlightened")

        return strings.filterNotNull().joinToString(", ")
    }

    companion object {
        fun create(feed: SpotFeed?): Spot? {

            var smallImageUrls = feed?.smallImageUrls
            var normalImageUrls = feed?.normalImageUrls

            if (feed?.id != null
                && feed.name != null
                && feed.creationDate != null
                && feed.description != null
                && normalImageUrls != null
                && smallImageUrls != null
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
                        normalImageUrls = normalImageUrls,
                        smallImageUrls = smallImageUrls,
                        spotEnum = SpotType.valueOf(feed.type),
                        needToPay = feed.needToPay ?: false,
                        shelteredFromRain = feed.shelteredFromRain ?: false,
                        hasFixedHours = feed.hasFixedHours ?: false,
                        hasLighting = feed.hasLighting ?: false,
                        commentCount = feed.commentCount ?: 0
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
    val creator: SkaterLightFeed? = null,
    val name: String? = null,
    val description: String? = null,
    val type: String? = null,
    var commentCount: Int? = null,
    val coordinate: Coordinate.CoordinateFeed? = null,
    var normalImageUrls: List<String>? = null,
    var smallImageUrls: List<String>? = null,
    var needToPay: Boolean? = null,
    var shelteredFromRain: Boolean? = null,
    var hasFixedHours: Boolean? = null,
    var hasLighting: Boolean? = null
) {


}
