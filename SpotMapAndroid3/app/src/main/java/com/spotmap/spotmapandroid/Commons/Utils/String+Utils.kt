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
