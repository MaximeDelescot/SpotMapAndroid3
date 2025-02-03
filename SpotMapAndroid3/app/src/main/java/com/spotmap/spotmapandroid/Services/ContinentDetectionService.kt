package com.spotmap.spotmapandroid.Services

import java.util.Locale

enum class ClosestContinent {
    AMERICA,
    ASIA,
    EUROPE,
    UNKNOWN
}

class ContinentDetectionService() {
    companion object {
        fun getNearestContinent(): ClosestContinent {
            val countryCode = Locale.getDefault().country.uppercase()

            return when (countryCode) {
                "FR", "DE", "ES", "IT", "GB", "NL", "BE", "SE", "CH", "AT", "PL", "RU", "PT", "GR", "NO", "DK", "FI", "IE", "HU", "CZ", "SK" -> ClosestContinent.EUROPE

                "US", "CA", "MX", "BR", "AR", "CO", "CL", "PE", "VE", "EC", "UY", "PY", "BO" -> ClosestContinent.AMERICA

                "CN", "JP", "IN", "KR", "TH", "VN", "PH", "ID", "MY", "SG", "HK", "TW", "PK", "BD", "LK" -> ClosestContinent.ASIA

                else -> ClosestContinent.UNKNOWN
            }
        }
    }
}