package com.chipmong.dms.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.format.DateUtils
import java.net.InetAddress
import java.net.UnknownHostException

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 06-Feb-20
 */
object NetworkUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectionManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectionManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val address = InetAddress.getByName("www.google.com");
            if (address.isReachable(10* DateUtils.SECOND_IN_MILLIS.toInt())){
                !address.equals("")
            } else{
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}