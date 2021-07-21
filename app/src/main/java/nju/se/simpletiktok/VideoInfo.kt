package nju.se.simpletiktok

import com.google.gson.annotations.SerializedName

class VideoInfo {
    @SerializedName("_id")
    var id = ""

    @SerializedName("feedurl")
    var videoUri = ""

    @SerializedName("avatar")
    var avatarUri = ""

    @SerializedName("nickname")
    var nickname = ""

    @SerializedName("description")
    var description = ""

    @SerializedName("likecount")
    var likeCounts = 0

    @SerializedName("thumbnails")
    var thumbnails = ""
}