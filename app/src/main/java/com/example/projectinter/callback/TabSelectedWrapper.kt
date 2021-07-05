package com.chipmong.dms.callback

import com.google.android.material.tabs.TabLayout

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 01-Jun-20
 */
class TabSelectedWrapper(private var onTabSelected: ((TabLayout.Tab?) -> (Unit))? = null) :
    TabLayout.OnTabSelectedListener {

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        onTabSelected?.invoke(tab)
    }
}