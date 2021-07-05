package com.chipmong.dms.adapter

import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 06-Feb-20
*/
abstract class BasePagerAdapter<T>(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    protected open var mData: List<T> = ArrayList()

    override fun getCount(): Int {
        return mData.size
    }

    override fun saveState(): Parcelable? {
        return null
    }
}