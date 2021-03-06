package nju.se.simpletiktok
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide

class CityListFragment : Fragment(R.layout.activity_city_list), VideoClickListener {
    private var videos: List<VideoInfo>? = null
    private var VideoList: RecyclerView? = null
    private var adapter: VideoListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_city_list, container, false)
        VideoList = root.findViewById(R.id.rcy_video_list)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        this.VideoList?.setLayoutManager(staggeredGridLayoutManager)//模仿抖音使用错排两列
        VideoInfoProvider.request(this::init)
        return root
    }

    private fun init() {
        videos = VideoInfoProvider.allVideoInfo
        adapter = VideoListAdapter(videos, this)
        adapter!!.videos = videos
        adapter!!.videoClickListener = this
        if (this.VideoList != null) this.VideoList!!.setAdapter(adapter)
    }

    override fun onVideoClick(clickedItemIndex: Int) {
        val intent = Intent(context, VideoWatchActivity::class.java)
        intent.putExtra("index", clickedItemIndex)
        startActivity(intent)
    }

    private inner class VideoListAdapter(
        var videos: List<VideoInfo>?,
        cityListActivity: VideoClickListener
    ) : RecyclerView.Adapter<VideoListAdapter.VideoItemViewHolder>() {
        var videoClickListener: VideoClickListener? = cityListActivity
        var viewHolderCount = 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.video_list_item, parent, false)
            val videoItemViewHolder = VideoItemViewHolder(view)
            val video: VideoInfo = videos!![viewHolderCount]
            Glide.with(view.context)
                .load(video.avatarUri)
                .centerCrop()
                .into(videoItemViewHolder.imgVideo)
            viewHolderCount++
            return videoItemViewHolder
        }

        override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {}

        override fun getItemCount(): Int {
            return this.videos!!.size
        }

        inner class VideoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
            val imgVideo: ImageView
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
    }
}

interface VideoClickListener {
    fun onVideoClick(clickedItemIndex: Int)
}