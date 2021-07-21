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
        viewPager.registerOnPageChangeCallback(PageChangeListener())
        viewPager.adapter = VideoPagerAdapter(this)
        viewPager.currentItem = 1
    }

    private inner class VideoPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        // Use two fake fragments to implement endless scrolling
        override fun getItemCount(): Int = VideoInfoProvider.pageNum + 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0, itemCount - 1 -> Fragment()
                else -> {
                    val videoInfo = VideoInfoProvider.allVideoInfo[position - 1]
                    VideoItemFragment.newInstance(
                        videoUri = videoInfo.videouri,
                        avatarUri = videoInfo.avatururi,
                        nickname = videoInfo.nickname,
                        description = videoInfo.description,
                        likeCount = videoInfo.likecounts)
                }
            }
        }
    }

    private inner class PageChangeListener : ViewPager2.OnPageChangeCallback() {
        /**
         * Check [position] and reset real position in pager for endless scrolling.
         * */
        override fun onPageSelected(position: Int) = when (position) {
            0 -> viewPager.setCurrentItem(VideoInfoProvider.pageNum, false)
            VideoInfoProvider.pageNum + 1 -> viewPager.setCurrentItem(1, false)
            else -> {
                super.onPageSelected(position - 1)
            }
        }
    }
}