package nju.se.simpletiktok

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

private const val TAG = "VideoInfoProvider"

object VideoInfoProvider {
    lateinit var allVideoInfo: List<VideoInfo>
    var pageNum by Delegates.notNull<Int>()

    fun request(callback: () -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://beiyou.bytedance.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getVideos().enqueue(object : Callback<List<VideoInfo>> {
            override fun onResponse(
                call: Call<List<VideoInfo>>,
                response: Response<List<VideoInfo>>,
            ) {
                Log.d(TAG, response.message())
                Log.d(TAG, response.code().toString())
                allVideoInfo = response.body()!!
                pageNum = allVideoInfo.size
                callback()
            }

            override fun onFailure(call: Call<List<VideoInfo>>, t: Throwable) {
                Log.d(TAG, "Fail to get video info.")
                Log.d(TAG, t.message.toString())
            }
        })
    }
}