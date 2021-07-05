package com.example.projectinter.ui.fragment

import com.chipmong.dms.ui.fragment.BaseFragment
import com.example.projectinter.R


class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun layoutContainerRes(): Int = R.layout.fragment_home

}