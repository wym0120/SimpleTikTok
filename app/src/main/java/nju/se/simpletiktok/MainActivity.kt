package nju.se.simpletiktok

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: 2021/7/20 Get real page num by api
private const val PAGE_NUM = 3
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.pager)
        viewPager.registerOnPageChangeCallback(PageChangeListener())
        viewPager.adapter = VideoPagerAdapter(this)
    }

    private inner class VideoPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        // Use two fake fragments to implement endless scrolling
        override fun getItemCount(): Int = PAGE_NUM + 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0, itemCount - 1 -> Fragment()
                // TODO: 2021/7/20 get args from api
                else -> VideoItemFragment.newInstance("todo", "todo", "todo", "todo", 0)
            }
        }
    }

    private fun getVideoInfo(): List<VideoInfo> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://beiyou.bytedance.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        var res = listOf<VideoInfo>()
        apiService.getVideos().enqueue(object : Callback<List<VideoInfo>> {
            override fun onResponse(
                call: Call<List<VideoInfo>>,
                response: Response<List<VideoInfo>>,
            ) {
                res = response.body()!!
            }

            override fun onFailure(call: Call<List<VideoInfo>>, t: Throwable) {
                Log.d(TAG, "Fail to get video info.")
            }
        })
        return res
    }

    private inner class PageChangeListener : ViewPager2.OnPageChangeCallback() {
        /**
         * Check [position] and reset real position in pager for endless scrolling.
         * */
        override fun onPageSelected(position: Int) = when (position) {
            0 -> viewPager.setCurrentItem(PAGE_NUM, false)
            PAGE_NUM + 1 -> viewPager.setCurrentItem(1, false)
            else -> {
                super.onPageSelected(position - 1)
            }
        }
    }
}