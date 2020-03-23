package com.github.zakru.hermittracker

import org.joda.time.DateTime
import org.joda.time.Instant
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat
import java.util.*
import kotlin.collections.ArrayList

class VideoSearcher {

    companion object {

        private val CHANNEL_IDS = listOf(
                "UClu2e7S8atp6tG2galK9hgg",
                "UC9lJXqw4QZw-HWaZH6sN-xw",
                "UC4O9HKe9Jt5yAhKuNv3LXpQ",
                "UCFKDEp9si4RmHFWJW1vYsMA",
                "UCuQYHhF6on6EXXO-_i_ClHQ",
                "UCR9Gcq0CMm6YgTzsDxAxjOQ",
                "UChi5MyXJLQuPni3dM19Ar3g",
                "UCuMJPFqazQI4SofSFEd-5zA",
                "UCZ9x-z3iOnIbJxVpm1rsu2A",
                "UCrEtZMErQXaSYy_JDGoU5Qw",
                "UCtWObtiLCNI_BTBHwEOZNqg",
                "UCcJgOennb0II4a_qi9OMkRA",
                "UChFur_NwVSbUozOcF_F2kMg",
                "UCDpdtiUfcdUCzokpRWORRqA",
                "UCodkNmk9oWRTIYZdr_HuSlg",
                "UC24lkOxZYna9nlXYBcJ9B8Q",
                "UC4YUKOBld2PoOLzk0YZ80lw",
                "UCRatys97ggrXVtQQBGRALkg",
                "UCu17Sme-KE87ca9OTzP0p7g",
                "UC_MkjhQr_D_lGlO3uu-GxyA",
                "UCU9pX8hKcrx06XfOB-VQLdw",
                "UCPK5G4jeoVEbUp5crKJl6CQ",
                "UCjI5qxhtyv3srhWr60HemRw"
        )
        val CHANNEL_NAMES = listOf(
                "BDoubleO100",
                "cubfan135",
                "docm77",
                "Etho",
                "FalseSymmetry",
                "Grian",
                "Hypnotizd",
                "impulseSV",
                "iskall85",
                "iJevin",
                "joehills",
                "Keralis",
                "Mumbo Jumbo",
                "rendog",
                "GoodTimesWithScar",
                "Stressmonster101",
                "Tango Tek",
                "TinfoilChef",
                "Vintage Beef",
                "xBCrafted",
                "Xisuma",
                "ZedaphPlays",
                "ZombieCleo"
        )
    }

    private var hermit = Hermit()
    private var lastTime = Instant.now().toString(ISODateTimeFormat.dateTime())

    fun search(config: HermitTrackerConfig): List<Video> {
        lastTime = Instant.now().toString(ISODateTimeFormat.dateTime())

        return searchVideos(config)
    }

    fun nextPage(config: HermitTrackerConfig): List<Video> {
        return searchVideos(config)
    }

    private fun searchVideos(config: HermitTrackerConfig): List<Video> {
        val response = hermit.videos("HermitCraft", lastTime)

        val filtered = ArrayList<Video>()
        for (result in response) {
            val resultTime = DateTime.parse(result.uploaded)
            if ((config.beginListingTime.isBefore(resultTime) || config.beginListingTime.isEqual(resultTime))
                    && !config.watchedVideos.contains(result.id))
                filtered.add(result)
        }

        lastTime = response[response.size-1].uploaded

        return filtered
    }
}