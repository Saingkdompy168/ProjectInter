package com.chipmong.dms.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.projectinter.R

public abstract class CustomDialog(context: Context) : Dialog(context) {

    private var view: View? = null

    override fun setContentView(view: View) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.setContentView(view)
        this.view = view
        window!!.setDimAmount(0.5f)
        val width = dialogWidth()
        window!!.setLayout(
            width.toInt(),
            dialogHeight()
        )
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.attributes.windowAnimations = windowAnimationStyle()
    }

    protected open fun windowAnimationStyle() : Int{
        return  R.style.DialogAnimationScaleInOut
    }

    /**
     * Override dialog width
     */
    protected open fun dialogWidth(): Int {
        return (context.resources.displayMetrics.widthPixels * 0.75).toInt()
    }
    /**
     * Override dialog height
     */
    protected open fun dialogHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }


    override fun setContentView(layoutResID: Int) {
        setContentView(LayoutInflater.from(context).inflate(layoutResID, null))
    }

    fun getView() = view!!

}