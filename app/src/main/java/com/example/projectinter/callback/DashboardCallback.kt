package com.chipmong.dms.callback

import android.location.Location

interface DashboardCallback {

    fun registerListener(listener : OnLocationChangedListener)

    fun unRegisterListener(listener : OnLocationChangedListener)

    fun onLocationChange(location: Location)

    fun onLanguageChanged()

    interface OnLocationChangedListener{
        fun onLocationChange(location : Location)

        fun onLanguageChanged()
    }
}