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

    /**
     * This function will async request all video info.
     *
     * Update video info and then call [callback] function when 200 OK, otherwise show failure message.
     *
     * */
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
                if (response.code() == 200 && response.body() != null) {
                    allVideoInfo = response.body()!!
                    pageNum = allVideoInfo.size
                    callback()
                } else {
                    Log.d(TAG, "Request fail, error code ${response.code()},${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<VideoInfo>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}