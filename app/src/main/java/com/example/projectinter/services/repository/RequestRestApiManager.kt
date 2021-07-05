package com.chipmong.dms.services.repository

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import androidx.annotation.RequiresApi
import com.chipmong.dms.callback.OnResponseListener
import com.chipmong.dms.callback.SingleCallbackWrapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 *
 *
 * @author Noel
 * @version
 * @created on 20-Feb-20
 */
object RequestRestApiManager {
    private var mActivity: Activity? = null
    private var mDialog: Dialog? = null
    fun init(activity: Activity) {
        mActivity = activity
    }

    /**
     * Request Single
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun <T> sendRequest(
        type: Any?,
        disposable: CompositeDisposable,
        observer: Single<T>,
        onResponseListener: OnResponseListener<T>
    ) {
        disposable.add(
            observer
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(SingleCallbackWrapper(mActivity, type, onResponseListener).apply {
                    onNewDialog = {
                        if (mActivity!=null && !mActivity!!.isDestroyed){
                            mDialog?.dismiss()
                            mDialog = it
                        }
                    }
                })
        )
    }
}