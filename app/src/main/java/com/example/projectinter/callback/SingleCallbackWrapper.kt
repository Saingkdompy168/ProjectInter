package com.chipmong.dms.callback

import android.app.Activity
import android.app.Dialog
import android.os.Build
import com.chipmong.dms.constants.Constants
import com.chipmong.dms.utils.DmsUtils
import com.example.projectinter.R
import com.google.gson.JsonParseException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.observers.DisposableSingleObserver
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException

/**
 *
 *
 * @author Noel
 * @version
 * @created on 20-Feb-20
 */
class SingleCallbackWrapper<T>(
    var activity: Activity? = null,
    var type: Any?,
    var onResponseListener: OnResponseListener<T>
) :
    DisposableSingleObserver<T>() {

    var onNewDialog: ((Dialog?) -> (Unit))? = null
    override fun onSuccess(t: T) {
        onResponseListener.onResponseSuccess(type, t)
    }

    override fun onError(e: Throwable) {
        handleError(type, e)
    }

    private fun handleError(
        type: Any?,
        e: Throwable
    ) {
        when (e) {
            is HttpException -> {
                val responseBody = e.response().errorBody()
                onResponseListener.apply {
                    val errorMessage =
                        if (responseBody != null) getErrorMessage(responseBody) else "Please check your connection and try again."
                    if (DmsUtils.isExpiredToken(e.code(), errorMessage)) {
                        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                activity != null && !activity!!.isDestroyed
                            } else {
                                TODO("VERSION.SDK_INT < JELLY_BEAN_MR1")
                            }
                        ) {
//                            val mDialog = activity?.showErrorDialog(
//                                "$errorMessage",
//                                activity!!.getString(R.string.app_name)
//                                , {
//                                    activity?.clearSession()
//                                    activity?.finish()
//                                }, null
//                            )
//                            onNewDialog?.invoke(mDialog)
                        }
                        onTokenExpired(type, Throwable(errorMessage), errorMessage)
                    } else {
                        onResponseFailed(type, Throwable(errorMessage))
                        onResponseFailed(type, e, errorMessage)
                    }
                }
            }
            is SocketTimeoutException -> {
                onResponseListener.apply {
                    onResponseFailed(
                        type,
                        Throwable("Time Out. Please check your connection and try again.")
                    )
                    onResponseFailed(
                        type,
                        e,
                        "Time Out. Please check your connection and try again."
                    )
                }
            }
            is IOException -> {
                onResponseListener.apply {
                    onResponseFailed(
                        type,
                        Throwable("Please check your connection and try again.")
                    )
                    onResponseFailed(type, e, "Please check your connection and try again.")
                }
            }
            is JsonParseException -> {
                onResponseListener.apply {
                    onResponseFailed(type, Throwable("${e.message}"))
                    onResponseFailed(type, e, "${e.message}")
                }
            }
            else -> onResponseListener.apply {
                onResponseFailed(type, e)
                onResponseFailed(type, e, "${e.message}")
            }
        }

    }

    private fun getErrorMessage(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString(Constants.MESSAGE)
        } catch (e: Exception) {
            if (activity != null) activity!!.getString(R.string.some_thing_went_wrong) else "Something went wrong. Try again later."
        }
    }
}