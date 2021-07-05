package com.chipmong.dms.callback

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 12-Mar-20
 */
interface OnObserverListener<T> {
    fun onNext(t : T){}
    fun onComplete(){}
    fun onError(t: Throwable){}
}