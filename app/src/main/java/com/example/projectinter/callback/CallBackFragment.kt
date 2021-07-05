package com.chipmong.dms.callback

interface CallBackFragment<T> {
    fun communicate(comm: T)
    fun getListData(mData: ArrayList<T>)
}