package com.chipmong.dms.callback

interface ActivityCallBack {
    fun registerRefreshListener(listener: OnRefreshDataListener)

    fun unRegisterRefreshListener(listener: OnRefreshDataListener)

    interface OnRefreshDataListener {
        fun onRefresh(
            startDate: String,
            endDate: String,
            employeeId: String,
            isExcludeSelf: Boolean
        )
    }
}