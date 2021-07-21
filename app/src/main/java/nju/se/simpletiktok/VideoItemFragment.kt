package nju.se.simpletiktok

import android.net.Uri
import android.os.Bundle
import android.view.View
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
    private var like = false

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
        player.setOnClickListener {
            if (player.isPlaying) player.pause()
            else player.start()
        }

        player.setVideoURI(Uri.parse(videoUri))
//        player.setVideoPath("android.resource://" + this.requireActivity().packageName + "/" + R.raw.bytedance)

        // auto start
        player.setOnPreparedListener { mp ->
            mp.isLooping = true
            if (!player.isPlaying) {
                player.start()
            }
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