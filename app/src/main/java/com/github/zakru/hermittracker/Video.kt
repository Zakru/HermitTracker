package com.github.zakru.hermittracker

class Video(
        val id: String,
        val uploaded: String,
        val uploadedFriendly: String,
        val uploadedFriendlyMobile: String,
        val uploader: Uploader,
        val title: String,
        val duration: Int,
        val friendlyDuration: String,
        val likeCount: String,
        val viewCount: Int,
        val commentCount: Int
) {
    class Uploader(
            val id: Int,
            val BeamName: String,
            val ChannelName: String,
            val DisplayName: String,
            val GooglePlusLink: String,
            val ProfilePicture: String,
            val TwitterName: String,
            val TwitchName: String,
            val WebsiteURL: String,
            val Active: Boolean,
            val Streaming: Boolean,
            val BeamStreaming: Boolean,
            val YTStreaming: Boolean,
            val ChannelID: String,
            val UploadPlaylistID: String
    )
}