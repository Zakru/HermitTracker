package com.github.zakru.hermittracker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuInflater
import android.widget.*
import com.squareup.picasso.Picasso

class VideoScrollView(context: Context, attributeSet: AttributeSet) : ScrollView(context, attributeSet) {

    private lateinit var activity: VideoListActivity
    private lateinit var config: HermitTrackerConfig

    private lateinit var container: LinearLayout
    private val searcher = VideoSearcher()

    private var loading: Boolean = true

    private val alreadyVideos = HashSet<String>()

    fun initialLoad(config: HermitTrackerConfig, activity: VideoListActivity) {
        this.config = config
        this.activity = activity

        container = findViewById(R.id.video_list_view)

        Thread {
            appendMore(searcher.search(config))
        }.start()
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val view = container.getChildAt(childCount-1)

        val diff = container.height - scrollY - height

        if (diff == 0) loadMore()

        super.onScrollChanged(l, t, oldl, oldt)
    }

    private fun loadMore() {
        if (loading) return
        loading = true

        Thread {
            appendMore(searcher.nextPage(config))
        }.start()
    }

    private fun appendMore(videos: List<Video>) {
        handler.post {
            val inflater = LayoutInflater.from(context)
            for (video in videos) {
                val view = inflater.inflate(R.layout.view_video_card, null)
                container.addView(view)

                view.setOnClickListener {
                    activity.launchVideo(video.id)
                }

                view.findViewById<TextView>(R.id.title_view).text = video.title
                view.findViewById<TextView>(R.id.channel_title_view).text = video.uploader.DisplayName

                view.findViewById<TextView>(R.id.options_button).setOnClickListener {
                    val popup = PopupMenu(context, it)
                    activity.menuInflater.inflate(R.menu.video_options_menu, popup.menu)
                    popup.menu.findItem(R.id.video_options_mark_watched).setOnMenuItemClickListener {
                        container.removeView(view)
                        config.watchedVideos.add(video.id)
                        activity.saveConfig()
                        true
                    }
                    popup.show()
                }

                Picasso.with(context)
                        .load(thumbnailURL(video.id))
                        .into(view.findViewById<ImageView>(R.id.thumbnail_view))
            }
            loading = false
        }
    }

    private fun thumbnailURL(id: String): String {
        return "https://i.ytimg.com/vi/$id/maxresdefault.jpg"
    }
}