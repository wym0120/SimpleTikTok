package nju.se.simpletiktok

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/invoke/video/invoke/video/")
    fun getVideos(): Call<List<VideoInfo>>
}