package com.chipmong.dms.constants

import android.text.format.DateUtils

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 24-Feb-20
 */
object LocationSettings {
    const val MIN_DISTANCE = 20f
    //        const val MIN_DISTANCE = 100f
    //        const val MAX_DISTANCE = 10000000f
    const val MAX_DISTANCE = 300f
    const val MIN_TIME: Long = 10000
    //        const val MIN_TIME: Long = 5000
//        const val FASTEST_INTERVAL: Long = 3000
    const val FASTEST_INTERVAL: Long = 5000
    const val MAX_TIME = 30 * DateUtils.SECOND_IN_MILLIS
    const val MAX_RADIUS = 50000.0
}