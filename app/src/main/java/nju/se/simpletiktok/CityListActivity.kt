package nju.se.simpletiktok

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*
import com.bumptech.glide.Glide


class CityListActivity : AppCompatActivity() {
    private val videos: ArrayList<VideoInfo>? = null
    private var VideoList: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_list)
        VideoList = findViewById<RecyclerView>(R.id.rcy_video_list)

        findViewById<ImageView>(R.id.img_video_play).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }





    private inner class VideoListAdapter:RecyclerView.Adapter<VideoListAdapter.VideoItemViewHolder>() {
        private var viewHolderCount = 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.video_list_item, parent, false)
            val videoItemViewHolder = VideoItemViewHolder(view)
            Glide.with(view.context)
                .load(video.feedurl)
                .centerCrop()
                .into(videoItemViewHolder.imgVideo)
            viewHolderCount++
            return videoItemViewHolder
        }

        override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {}
        override fun getItemCount(): Int {
            return 0;
        }

        inner class VideoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
            private val imgVideo: ImageView
            private val imgVideoPlay: ImageView
            override fun onClick(view: View) {
                videoClickListener?.onVideoClick(adapterPosition)
            }

            init {
                imgVideo = itemView.findViewById(R.id.img_video)
                imgVideoPlay = itemView.findViewById(R.id.img_video_play)
                imgVideo.setOnClickListener(this)
                imgVideoPlay.setOnClickListener(this)
            }
        }
//
//        interface VideoClickListener {
//            fun onVideoClick(clickedItemIndex: Int)
//        }
    }
}