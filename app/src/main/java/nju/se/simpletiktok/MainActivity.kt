package nju.se.simpletiktok

import android.content.Intent
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private val REQUEST_VIDEO_CAPTURE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VideoInfoProvider.request(this::initViewPager)
        findViewById<TextView>(R.id.city).setOnClickListener {
            val intent = Intent(this, CityListContainerActivity::class.java)
            startActivity(intent)
        }
        initRecord()
    }

    private fun initRecord(){
        val recordView = findViewById<TextView>(R.id.record)
        recordView.setOnClickListener { dispatchTakeVideoIntent() }
    }
    private fun initViewPager() {
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = VideoPagerAdapter(this)
        viewPager.currentItem = 0
    }


    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        println(requestCode == 1)
        println(resultCode == -1)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val videoUri: Uri = intent!!.data!!
//            Toast.makeText(this,videoUri.toString(),Toast.LENGTH_LONG).show()
            val bundle = Bundle()
            bundle.putString("videoUri", videoUri.toString())
            val jumpIntent = Intent(this,VideoUploadActivity::class.java)
            jumpIntent.putExtras(bundle)
            startActivity(jumpIntent)
        }
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
