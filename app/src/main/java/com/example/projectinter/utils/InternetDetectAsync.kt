package com.chipmong.dms.utils

import android.os.AsyncTask

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 04-Mar-20
 */
class InternetDetectAsync(private var onDetectInternetFinished: ((Boolean?) -> (Unit))? = null) :
    AsyncTask<Unit, Unit, Boolean>() {


    override fun doInBackground(vararg params: Unit?): Boolean {
        return NetworkUtils.isInternetAvailable()
    }

    override fun onPostExecute(result: Boolean?) {
        onDetectInternetFinished?.invoke(result)
    }
}