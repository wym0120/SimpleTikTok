package nju.se.simpletiktok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Fragment;

class CityListContainerActivity : AppCompatActivity() {//这里用来放视屏列表的fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_list_container)
        //setContentView(R.layout.activity_city_list)

    }
}