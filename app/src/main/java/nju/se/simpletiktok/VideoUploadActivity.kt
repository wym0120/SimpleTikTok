package nju.se.simpletiktok

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import android.widget.VideoView
import androidx.appcompat.app.AlertDialog

class VideoUploadActivity : AppCompatActivity() {
    private lateinit var videoView:VideoView
    private lateinit var videoUri:Uri
    private lateinit var progressBar:ProgressBar
    private lateinit var handler:Handler
    var mProgress:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_upload)
        initHandler()
        initUri()
        initButton()
        initVideoView()
        initBar()
    }

    private fun initHandler(){
        handler = object: Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if(msg.what == 1){
                    progressBar.progress = mProgress
                }else{
                    showAlert()
                }
            }
        }
    }
    private fun initUri(){
        val str: String?
        if(intent!=null){
            str = intent.getStringExtra("videoUri")
            //get the true uri of this video
            videoUri = Uri.parse(str)
        }
    }
    private fun initButton(){
        val upload = findViewById<TextView>(R.id.upload)
        upload.setOnClickListener {
            videoView.pause()
            handleUpload()
        }
    }

    private fun initVideoView(){
        videoView = findViewById(R.id.preview)
        videoView.setVideoURI(videoUri)
        videoView.setOnPreparedListener {mp ->
            mp.isLooping = true
            videoView.start()
        }
    }

    private fun initBar(){
        progressBar = findViewById(R.id.uploadProgress)
        progressBar.visibility= View.GONE
    }

    private fun handleUpload(){
        progressBar.visibility=View.VISIBLE
        Thread {
            while (true) {
                if (mProgress <= 100) {
                    try {
                        handler.sendEmptyMessage(1)
                        Thread.sleep(200)
                        mProgress += 10
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                } else {
                    handler.sendEmptyMessage(2)
                    break
                }


            }
        }.start()

    }

    private fun showAlert(){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("上传完成")
        dialog.setMessage("点击'完成'返回主界面")
        dialog.setCancelable(false)
        dialog.setPositiveButton("完成"
        ) { _, _ ->
            val jumpBack = Intent(this@VideoUploadActivity, MainActivity::class.java)
            startActivity(jumpBack)
        }
        dialog.show()
    }


}