package com.chipmong.dms.utils

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation


/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 13-Mar-20
 */
object AnimationUtils {

    fun expand(v: View) {
        if (v.visibility == View.VISIBLE) {
            return
        }
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = v.measuredWidth

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.width = 1
        v.visibility = View.VISIBLE
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.width = if (interpolatedTime == 1f)
                    targetHeight
                else
                    (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        a.duration = ((targetHeight / v.context.resources.displayMetrics.density).toInt()).toLong()
        v.startAnimation(a)
//        v.visibility = View.VISIBLE
//        val animation = AnimationUtils.loadAnimation(v.context, R.anim.abc_slide_in_top)
//        animation.duration = 500
//        v.startAnimation(animation)
    }

    fun collapse(v: View) {
        val initialHeight = v.measuredWidth
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.width = initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        a.duration = ((initialHeight / v.context.resources.displayMetrics.density).toInt()).toLong()
        v.startAnimation(a)

//        val animation = android.view.animation.AnimationUtils.loadAnimation(v.context, R.anim.abc_slide_out_top)
//        animation.duration = 500
//        animation.setAnimationListener(object : Animation.AnimationListener{
//            override fun onAnimationRepeat(animation: Animation?) {
//
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                v.visibility = View.GONE
//            }
//
//            override fun onAnimationStart(animation: Animation?) {
//
//            }
//
//        })
//        v.startAnimation(animation)
    }


    fun expand1(v: View) {
        val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            (v.parent as View).width,
            View.MeasureSpec.EXACTLY
        )
        val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
        val targetHeight = v.measuredWidth
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.width = 1
        v.visibility = View.VISIBLE
        val a: Animation = object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation
            ) {
                v.layoutParams.width =
                    if (interpolatedTime == 1f) targetHeight else (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        // Expansion speed of 1dp/ms
        a.setDuration(
            (targetHeight / v.context.resources.displayMetrics.density).toLong()
        )
        v.startAnimation(a)
    }

    fun collapse1(v: View) {
        val initialHeight = v.measuredWidth
        val a: Animation = object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation
            ) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.width =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        // Collapse speed of 1dp/ms
        a.setDuration(
            (initialHeight / v.context.resources.displayMetrics.density).toLong()
        )
        v.startAnimation(a)
    }

}