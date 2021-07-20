package nju.se.simpletiktok;

import com.google.gson.annotations.SerializedName;

public class VideoInfo{
    @SerializedName("_id")
    public String id;
    @SerializedName("feedurl")
    public String videouri;
    @SerializedName("avatar")
    public String avatururi;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("description")
    public String description;
    @SerializedName("likecount")
    public int likecounts;
    @SerializedName("thumbnails")
    public String thumbnails;
}
