package nju.se.simpletiktok

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import nju.se.simpletiktok.databinding.FragmentVideoItemBinding
import kotlin.properties.Delegates

private const val ARG_NICKNAME = "nickname"
private const val ARG_VIDEO_URI = "videoUri"
private const val ARG_AVATAR_URI = "avatarUri"
private const val ARG_LIKE_COUNT = "likeCount"
private const val ARG_DESCRIPTION = "description"

class VideoItemFragment : Fragment(R.layout.fragment_video_item) {
    private lateinit var nickname: String
    private lateinit var description: String
    private lateinit var videoUri: String
    private lateinit var avatarUri: String
    private var likeCount by Delegates.notNull<Int>()

    private var _binding: FragmentVideoItemBinding? = null
    private val binding get() = _binding

    // state fields
    private var started = false
    private var like = false
    private val mHandler = Handler(Looper.getMainLooper())
    private val updateSeekBar = object : Runnable {
        override fun run() {
            if (binding != null) {
                if (binding!!.videoView.isPlaying) {
                    binding!!.seekBar.max = binding?.videoView!!.duration
                    binding!!.seekBar.progress = binding?.videoView!!.currentPosition
                }
                mHandler.postDelayed(this, 200)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            description = it.getString(ARG_DESCRIPTION)!!
            nickname = it.getString(ARG_NICKNAME)!!
            videoUri = it.getString(ARG_VIDEO_URI)!!
            avatarUri = it.getString(ARG_AVATAR_URI)!!
            likeCount = it.getInt(ARG_LIKE_COUNT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = FragmentVideoItemBinding.bind(view)
        initStaticResources()
        initLikeBtn()
        initPlayer()
        initSeekBar()
        initStartPauseBtn()
        mHandler.post(updateSeekBar)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * Initialize some static resources by [description], [avatarUri], [nickname], [likeCount]
     * */
    private fun initStaticResources() {
        binding!!.textDesc.text = description
        binding!!.textNickname.text = nickname
        binding!!.textLikeCount.text = likeCount.toString()
        // TODO: 2021/7/20 uncomment this line to load avatar from uri
        Picasso.get().load(Uri.parse(avatarUri)).into(binding!!.avatar)
    }

    private fun initLikeBtn() {
        binding!!.likeBtn.setOnClickListener {
            if (like) likeCount-- else likeCount++
            // TODO: 2021/7/20 Change image view according to like state
            like = !like
            binding!!.textLikeCount.text = likeCount.toString()
        }
    }

    private fun initPlayer() {
        val player = binding!!.videoView
        player.setOnClickListener { changeState() }

        // TODO: 2021/7/20 replace local video with video online
//        player.setVideoURI(Uri.parse(videoUri))
        player.setVideoPath("android.resource://" + this.requireActivity().packageName + "/" + R.raw.bytedance)

        player.setOnPreparedListener { mp ->
            mp.setOnSeekCompleteListener {
                if (!started) {
                    player.start()
                }
            }
        }
    }

    private fun initStartPauseBtn() {
        val startPauseBtn = binding!!.startPauseBtn
        startPauseBtn.setOnClickListener { changeState() }
    }

    private fun initSeekBar() {
        val seekBar = binding!!.seekBar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val player = binding!!.videoView
                player.seekTo(seekBar!!.progress)
            }
        })
    }

    private fun changeState() {
        started = !started
        if (started) {
            binding!!.videoView.start()
        } else {
            binding!!.videoView.pause()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            videoUri: String,
            avatarUri: String,
            nickname: String,
            description: String,
            likeCount: Int,
        ) =
            VideoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NICKNAME, nickname)
                    putString(ARG_AVATAR_URI, avatarUri)
                    putString(ARG_VIDEO_URI, videoUri)
                    putString(ARG_DESCRIPTION, description)
                    putInt(ARG_LIKE_COUNT, likeCount)
                }
            }
    }
}