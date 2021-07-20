package nju.se.simpletiktok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlin.properties.Delegates

private const val ARG_NICKNAME = "nickname"
private const val ARG_VIDEO_URI = "videoUri"
private const val ARG_AVATAR_URI = "avatarUri"
private const val ARG_LIKE_COUNT = "likeCount"
private const val ARG_DESCRIPTION = "description"

class VideoItemFragment : Fragment() {
    private lateinit var nickname: String
    private lateinit var description: String
    private lateinit var videoUri: String
    private lateinit var avatarUri: String
    private var likeCount by Delegates.notNull<Int>()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_item, container, false)
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