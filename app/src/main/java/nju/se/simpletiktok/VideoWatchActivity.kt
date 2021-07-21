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
    private var videos: List<VideoInfo>? = null
    var currentVideo : VideoInfo?=null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_watch)
        VideoInfoProvider.request(this::initViewPager)
//        if (intent.extras == null || intent.extras!!.getParcelableArrayList<Parcelable>("videos") == null) {
//            finish()
//            return
//        }
        //videos = intent.extras!!.getParcelableArrayList<Parcelable>("videos")
//        this.nickname= currentVideo!!.nickname
//        this.description=currentVideo!!.description
//        this.avatarUri=currentVideo!!.avatarUri
//        this.likeCount=currentVideo!!.likeCounts
//        this.videoUri=currentVideo!!.videoUri
    }
    private fun initViewPager() {
        videos =VideoInfoProvider.allVideoInfo

        index = intent.extras!!.getInt("index")
        currentVideo = videos?.get(this.index) as VideoInfo
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = VideoPagerAdapter(this)
        viewPager.currentItem = index
    }

    private inner class VideoPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        // Use two fake fragments to implement endless scrolling
        override fun getItemCount(): Int = VideoInfoProvider.pageNum
        override fun createFragment(position: Int): Fragment {
            val videoInfo = VideoInfoProvider.allVideoInfo[position]
            return VideoItemFragment.newInstance(
                videoUri = currentVideo!!.videoUri,
                avatarUri = currentVideo!!.avatarUri,
                nickname = currentVideo!!.nickname,
                description = currentVideo!!.description,
                likeCount = currentVideo!!.likeCounts)
        }
    }
}