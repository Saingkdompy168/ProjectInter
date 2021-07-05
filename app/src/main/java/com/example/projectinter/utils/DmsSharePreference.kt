package com.chipmong.dms.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


/**
 *
 *
 * @author
 * @version
 * @created on 10-Feb-20
 */
class DmsSharePreference private constructor() {

    private val mGson = Gson()

    companion object {
        private val ourInstance = DmsSharePreference()
        private var mSharedPreferences: SharedPreferences? = null
        private var mEditor: SharedPreferences.Editor? = null

        @SuppressLint("CommitPrefEdits")
        fun getInstance(context: Context): DmsSharePreference {
            if (mSharedPreferences == null) {
                mSharedPreferences = context.getSharedPreferences("MediaApp", Context.MODE_PRIVATE)
            }

            if (mEditor == null) {
                mEditor = mSharedPreferences!!.edit()
            }
            return ourInstance
        }
    }

}