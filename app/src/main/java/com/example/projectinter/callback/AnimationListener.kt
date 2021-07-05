package com.chipmong.dms.callback

import android.animation.Animator

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 12-Mar-20
 */
interface AnimationListener {

    fun onAnimationRepeat(animation: Animator?) {

    }

    fun onAnimationEnd(animation: Animator?) {

    }

    fun onAnimationCancel(animation: Animator?) {

    }

    fun onAnimationStart(animation: Animator?) {

    }
}