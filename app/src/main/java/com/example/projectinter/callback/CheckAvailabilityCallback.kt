package com.chipmong.dms.callback


interface CheckAvailabilityCallback {

    fun registerRefreshListener(listener: OnCheckAvailableRefreshListener)

    fun unRegisterRefreshListener(listener: OnCheckAvailableRefreshListener)

    fun onProductModify()

    fun onUpdateViewType()

    interface OnCheckAvailableRefreshListener {
        fun onNotifyDataSetChanged()

        fun onUpdateViewType()
    }
}