package com.example.projectinter.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.chipmong.dms.extensions.hide
import com.chipmong.dms.extensions.show
import com.example.projectinter.R
import kotlinx.android.synthetic.main.layout_tool_bar_title_center.view.*

class BaseToolbar(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_base_toolbar, this)
    }

    fun setToolbarTitle(title: String) {
        textViewActionBarTitle?.text = title
    }

    fun setToolbarTitle(@StringRes title: Int) {
        textViewActionBarTitle?.setText(title)
    }

    fun setToolBarIconRes(icon: Int) {
        imageToolbarIcon?.setImageResource(icon)
    }

    fun setToolbarActionIconRes(icon: Int) {
        imageToolbarAction?.setImageResource(icon)
    }


    fun hideToolbarActionIcon() {
        imageToolbarIcon?.hide()
    }

    fun showToolbarActionIcon() {
        imageToolbarIcon?.show()
    }


//    fun setActionButtonText(text: String?) {
//        text?.let {
//            btn_action_button?.setText(text)
//        }
//    }
//
//    fun setActionButtonIcon(icon: Int?) {
//        icon?.let {
//            btn_action_button?.setIcon(icon)
//        }
//
//    }
}