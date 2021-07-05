package com.chipmong.dms.callback

/**
 *
 *
 * @author
 * @version
 * @created on 04-Feb-2020
 */
interface OnResponseListener<in T> {
    fun onResponseSuccess(type: Any?, data: T?)
    fun onResponseFailed(type: Any?, t: Throwable){}
    fun onResponseFailed(type: Any?, t: Throwable, errorMessage: String){}
    fun onTokenExpired(type: Any?, t: Throwable, errorMessage: String){}
}