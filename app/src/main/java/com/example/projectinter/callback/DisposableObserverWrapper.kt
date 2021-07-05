package com.chipmong.dms.callback

import io.reactivex.observers.DisposableObserver

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 12-Mar-20
 */
class DisposableObserverWrapper<T>(private var onObserverListener: OnObserverListener<T>? = null) :
    DisposableObserver<T>() {
    override fun onComplete() {
        onObserverListener?.onComplete()
    }

    override fun onNext(t: T) {
        onObserverListener?.onNext(t)
    }

    override fun onError(e: Throwable) {
        onObserverListener?.onError(e)
        handleError(e)
    }

    private fun handleError(e: Throwable) {

    }
}