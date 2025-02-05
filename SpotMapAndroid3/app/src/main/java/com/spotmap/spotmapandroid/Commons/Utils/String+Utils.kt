package com.spotmap.spotmapandroid.Commons.Utils

import com.spotmap.spotmapandroid.Services.ClosestContinent
import com.spotmap.spotmapandroid.Services.ContinentDetectionService.Companion.getNearestContinent
import com.spotmap.spotmapandroid.Services.StorageService
import java.net.URL
import kotlin.collections.get

fun String.convertToFastestUrl(): String {
    val currentContinent = getNearestContinent()
    val defaultUrl = StorageService.storageUrls[ClosestContinent.AMERICA] ?: return this
    val fastestUrl = StorageService.storageUrls[currentContinent] ?: defaultUrl

    return try {
        this.replace(defaultUrl, fastestUrl)
    } catch (e: Exception) {
        this
    }
}


fun String.removeTokenParam(): String {
    return this.removeParameter("token")
}

private fun String.removeParameter(parameterName: String): String {
    val urlParts = this.split("?")

    if (urlParts.size > 1) {
        val baseUrl = urlParts[0]
        val query = urlParts[1]

        val newQuery = query.split("&")
            .filter { it.split("=")[0] != parameterName }
            .joinToString("&")

        return if (newQuery.isNotEmpty()) {
            "$baseUrl?$newQuery"
        } else {
            baseUrl
        }
    }

    return this
}
