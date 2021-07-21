package nju.se.simpletiktok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VideoInfoProvider.request(this::initViewPager)
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = VideoPagerAdapter(this)
        viewPager.currentItem = 0
    }

    private inner class VideoPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        // Use two fake fragments to implement endless scrolling
        override fun getItemCount(): Int = VideoInfoProvider.pageNum
        override fun createFragment(position: Int): Fragment {
            val videoInfo = VideoInfoProvider.allVideoInfo[position]
            return VideoItemFragment.newInstance(
                videoUri = videoInfo.videoUri,
                avatarUri = videoInfo.avatarUri,
                nickname = videoInfo.nickname,
                description = videoInfo.description,
                likeCount = videoInfo.likeCounts)
        }
    }
}
