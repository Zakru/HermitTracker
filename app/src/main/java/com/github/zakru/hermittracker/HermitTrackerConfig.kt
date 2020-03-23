package com.github.zakru.hermittracker

import org.joda.time.DateTime
import java.util.*
import kotlin.collections.HashSet

class HermitTrackerConfig {

    companion object {
        val SEASON_BEGIN_DATE: DateTime = DateTime.parse("2020-02-28T15:00:00.000Z")
    }

    var beginListingTime: DateTime = SEASON_BEGIN_DATE
    val watchedVideos: HashSet<String> = HashSet()
}