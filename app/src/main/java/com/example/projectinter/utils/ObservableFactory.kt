package com.chipmong.dms.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 12-Mar-20
 */
object ObservableFactory {

    /**
     * Create observable from list
     */
    fun <T> create(mData: List<T>): Observable<T> {
        return Observable.create {
            for (outlet in mData) {
                if (!it.isDisposed) {
                    it.onNext(outlet)
                }
            }

            if (!it.isDisposed) {
                it.onComplete()
            }
        }
    }

    /**
     * Call to add observable
     */
    fun <T> addComposite(dis : CompositeDisposable, mData: List<T>, observer : DisposableObserver<T>) {
        dis.add(this.create(mData).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer))
    }
}