package com.chipmong.dms.callback

import android.animation.Animator

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 12-Mar-20
 */
class AnimationListenerWrapper(private var listener: AnimationListener? = null) :
    Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) {
        listener?.onAnimationRepeat(animation)
    }

    override fun onAnimationEnd(animation: Animator?) {
        listener?.onAnimationEnd(animation)
    }

    override fun onAnimationCancel(animation: Animator?) {
        listener?.onAnimationCancel(animation)
    }

    override fun onAnimationStart(animation: Animator?) {
        listener?.onAnimationStart(animation)
    }
}