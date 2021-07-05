package com.chipmong.dms.callback

import com.chipmong.dms.utils.InternetDetectAsync

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 17-Mar-20
 */
class OnlineOfflineDataRetriever(private var onlineOfflineListener: OnlineOfflineListener? = null) {

    fun detectInternet(){
        InternetDetectAsync {
            it?.let {
                if (it) {
                    onlineOfflineListener?.onGetDataOnline()
                } else {
                    onlineOfflineListener?.onGetDataOffline()
                }
            }
        }.execute()
    }

    interface OnlineOfflineListener {
        fun onGetDataOnline()
        fun onGetDataOffline()
    }
}