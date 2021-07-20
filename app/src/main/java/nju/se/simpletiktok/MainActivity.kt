package nju.se.simpletiktok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

// TODO: 2021/7/20 Get real page num by api
private const val PAGE_NUM = 3

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.pager)
        viewPager.registerOnPageChangeCallback(PageChangeListener())
        viewPager.adapter = VideoPagerAdapter(this)
    }

    private inner class VideoPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        // Use two fake fragments to implement endless scrolling
        override fun getItemCount(): Int = PAGE_NUM + 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0, itemCount - 1 -> Fragment()
                // TODO: 2021/7/20 get args from api
                else -> VideoItemFragment.newInstance("todo", "todo", "todo", "todo", 0)
            }
        }
    }

    private inner class PageChangeListener : ViewPager2.OnPageChangeCallback() {
        /**
         * Check [position] and reset real position in pager for endless scrolling.
         * */
        override fun onPageSelected(position: Int) = when (position) {
            0 -> viewPager.setCurrentItem(PAGE_NUM, false)
            PAGE_NUM + 1 -> viewPager.setCurrentItem(1, false)
            else -> {
                super.onPageSelected(position - 1)
            }
        }
    }
}