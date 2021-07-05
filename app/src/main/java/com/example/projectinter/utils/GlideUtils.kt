package com.chipmong.dms.utils

import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.example.projectinter.R

/**
 *
 *
 * @author Noel
 * @version
 * @created on 04-Mar-20
 */
object GlideUtils {

//    fun defaultRequestOption(): RequestOptions {
//        return requestOption(R.drawable.ic_splash_screen_logo, R.drawable.ic_splash_screen_logo, false)
//    }

    fun requestOption(holder: Int, error: Int, skipMemoryCache: Boolean): RequestOptions {
        return RequestOptions().placeholder(holder).error(error).skipMemoryCache(skipMemoryCache).format(DecodeFormat.PREFER_RGB_565)
    }

    fun requestOptionFormatRGB_565(): RequestOptions {
        return RequestOptions().format(DecodeFormat.PREFER_RGB_565)
    }

//    fun getUrl(name: String): String {
//        return BuildConfig.BASE_URL_IMAGE + "uploads/images/" + name
//    }
}