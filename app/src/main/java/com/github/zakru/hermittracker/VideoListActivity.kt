package com.github.zakru.hermittracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import org.joda.time.Chronology
import org.joda.time.DateTime
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class VideoListActivity : AppCompatActivity() {

    private lateinit var videoListView: LinearLayout

    private lateinit var gson: Gson
    private lateinit var configFile: File
    private lateinit var config: HermitTrackerConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

        gson = GsonBuilder().setPrettyPrinting().registerTypeAdapter(DateTime::class.java, HermitTypeAdapters.DATE_TIME).create()
        configFile = File(applicationContext.filesDir, "config")
        if (configFile.exists()) {
            config = gson.fromJson(FileReader(configFile), HermitTrackerConfig::class.java)
        } else {
            config = HermitTrackerConfig()
            val writer = FileWriter(configFile)
            writer.write(gson.toJson(config, HermitTrackerConfig::class.java))
            writer.close()
        }

        findViewById<VideoScrollView>(R.id.video_scroll_view).initialLoad(config, this)
    }

    fun launchVideo(id: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://www.youtube.com/watch?v=$id")
        startActivity(intent)
    }

    fun saveConfig() {
        val writer = FileWriter(configFile)
        writer.write(gson.toJson(config, HermitTrackerConfig::class.java))
        writer.close()
    }
}
