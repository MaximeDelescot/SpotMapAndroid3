package com.spotmap.spotmapandroid.Class

import java.util.Date

class Publication (val id: String,
                   val creationDate: Date = Date(System.currentTimeMillis()),
                   val description: String?,
                   val videoUrl: String?,
                   val creator: SkaterLight,
                   val spot: SpotLight
) {

    companion object {
        fun create(feed: PublicationFeed?): Publication? {

            val creator = SkaterLight.create(feed = feed?.creator)
            val spotLight = SpotLight.create(feed = feed?.spot)

            if (feed?.id != null
                && feed.creationDate != null
                && creator != null
                && spotLight != null
                ) {

                return Publication(
                    id = feed.id,
                    creationDate =  feed.creationDate,
                    description = feed.description,
                    videoUrl = feed.videoUrl,
                    creator = creator,
                    spot = spotLight
                )
            } else {
                return null
            }
        }
    }
}

class PublicationFeed (val id: String? = null,
                   val creationDate: Date? = null,
                   val description: String? = null,
                   val videoUrl: String? = null,
                   val creator: SkaterLightFeed? = null,
                   val spot: SpotLightFeed? = null
) {

}

class PublicationRef(val id: String, val spotId: String) {

    companion object {
        fun create(feed: PublicationRefFeed?): PublicationRef? {
            if (feed?.id != null && feed.spotId != null) {
                return PublicationRef(id = feed.id,
                    spotId = feed.spotId)
            } else {
                return null
            }
        }
    }
}

class PublicationRefFeed(val id: String? = null,
                         val spotId: String? = null) {

}