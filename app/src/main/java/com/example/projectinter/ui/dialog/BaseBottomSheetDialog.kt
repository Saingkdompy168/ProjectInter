package com.chipmong.dms.ui.dialog

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


abstract class BaseBottomSheetDialog(context: Context, themResId: Int) : BottomSheetDialog(context, themResId) {
    constructor(context: Context) : this(context, 0)

    var onBottomSheetClickListener: OnBottomSheetClickListener? = null

    abstract fun layoutResId(): Int


    override fun onStart() {
        super.onStart()
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        val bottomSheet = findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId())
        initView()
        initEvent()
    }

    protected open fun initView() {}
    protected open fun initEvent() {}

    interface OnBottomSheetClickListener {
        fun onBottomSheetClickListener(view: View)
    }


//    protected fun showWaitingProgress(){
//        layout_progress_bar?.visibility = View.VISIBLE
//    }
//
//    protected fun hideWaitingProgress(){
//        layout_progress_bar?.visibility = View.GONE
//    }

    protected fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}