package nju.se.simpletiktok

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlin.properties.Delegates

class VideoWatchActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var nickname: String
    private lateinit var description: String
    private lateinit var videoUri: String
    private lateinit var avatarUri: String
    private var likeCount by Delegates.notNull<Int>()
    private var index = 0
    private var videos: List<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_video_watch)
        setContentView(R.layout.activity_main)
        VideoInfoProvider.request(this::initViewPager)
        if (intent.extras == null || intent.extras!!.getParcelableArrayList<Parcelable>("videos") == null) {
            finish()
            return
        }
        videos = intent.extras!!.getParcelableArrayList<Parcelable>("videos")
        index = intent.extras!!.getInt("index")

        val currentVideo : VideoInfo
        currentVideo = videos?.get(this.index) as VideoInfo

        this.nickname=currentVideo.nickname
        this.description=currentVideo.description
        this.avatarUri=currentVideo.avatururi
        this.likeCount=currentVideo.likecounts
        this.videoUri=currentVideo.avatururi

    }
    private fun initViewPager() {
        viewPager = findViewById(R.id.pager)
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
                        videoUri = videoUri,
                        avatarUri = avatarUri,
                        nickname = nickname,
                        description = description,
                        likeCount = likeCount)
                }
            }
        }
    }
}