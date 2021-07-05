package com.example.projectinter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.chipmong.dms.adapter.BasePagerAdapter
import com.example.projectinter.models.ViewPagerModel

class HomePagerAdapter(fm: FragmentManager) : BasePagerAdapter<ViewPagerModel>(fm) {
    constructor(fm: FragmentManager, callPlanEntities: List<ViewPagerModel>) : this(fm) {
        this.mData = callPlanEntities
    }

    override fun getItem(position: Int): Fragment {
        return mData[position].fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mData[position].image
    }
}