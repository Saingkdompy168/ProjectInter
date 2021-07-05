package com.chipmong.dms.callback

import androidx.viewpager.widget.ViewPager

class ViewPagerWrapper(private var viewPager: ((Int) -> (Unit))? = null) :
    ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        viewPager?.invoke(position)
    }
}