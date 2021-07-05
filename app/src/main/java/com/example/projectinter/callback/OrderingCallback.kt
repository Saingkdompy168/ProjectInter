package com.chipmong.dms.callback


interface OrderingCallback {
    fun registerRefreshListener(listener: OnOrderRefreshListener)

    fun unRegisterRefreshListener(listener: OnOrderRefreshListener)

    fun onProductModify()

    fun onUpdateViewType()

    fun onFilterModify(filter : Any){}

    interface OnOrderRefreshListener {
        fun onNotifyDataSetChanged()

        fun onUpdateViewType()

        fun onFilterModify(filter : Any){}

    }
}