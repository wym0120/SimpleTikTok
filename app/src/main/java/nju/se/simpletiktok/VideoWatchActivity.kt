package nju.se.simpletiktok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class VideoWatchActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private var index = 0
    private var videos: List<VideoInfo>? = null
    var currentVideo: VideoInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_watch)
        VideoInfoProvider.request(this::initViewPager)
    }

    private fun initViewPager() {
        videos = VideoInfoProvider.allVideoInfo
        index = intent.extras!!.getInt("index")
        currentVideo = videos?.get(this.index) as VideoInfo
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = VideoPagerAdapter(this)
        viewPager.currentItem = index
    }

    private inner class VideoPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = VideoInfoProvider.pageNum
        override fun createFragment(position: Int): Fragment {
            return VideoItemFragment.newInstance(
                videoUri = currentVideo!!.videoUri,
                avatarUri = currentVideo!!.avatarUri,
                nickname = currentVideo!!.nickname,
                description = currentVideo!!.description,
                likeCount = currentVideo!!.likeCounts
            )
        }
    }
}