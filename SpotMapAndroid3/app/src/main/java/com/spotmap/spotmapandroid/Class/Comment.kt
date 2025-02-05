package com.spotmap.spotmapandroid.Class

import java.util.Date
import java.util.UUID

class Comment(val id: String = UUID.randomUUID().toString(),
              val content: String,
              val creator: SkaterLight,
              val creationDate: Date = Date(System.currentTimeMillis())) {

    companion object {
        fun create(feed: CommentFeed?): Comment? {

            if (feed?.id != null
                && feed.content != null
                && feed.creationDate != null
            ) {
                val creator = SkaterLight.create(feed.creator)

                if (creator != null) {
                    return Comment(
                        id = feed.id,
                        content = feed.content,
                        creator = creator,
                        creationDate = feed.creationDate
                    )
                }
            }
            return null
        }
    }
}

class CommentFeed(val id: String? = null,
                  val content: String? = null,
                  val creator: SkaterLightFeed? = null,
                  val creationDate: Date? = null) {
}