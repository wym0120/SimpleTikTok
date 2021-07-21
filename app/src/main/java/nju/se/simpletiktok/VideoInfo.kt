package nju.se.simpletiktok

import com.google.gson.annotations.SerializedName

class VideoInfo {
    @SerializedName("_id")
    var id: String = ""

    @SerializedName("feedurl")
    var videouri: String = ""

    @SerializedName("avatar")
    var avatururi: String = ""

    @SerializedName("nickname")
    var nickname: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("likecount")
    var likecounts = 0

    @SerializedName("thumbnails")
    var thumbnails: String = ""
}