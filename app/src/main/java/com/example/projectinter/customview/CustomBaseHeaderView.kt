package com.example.projectinter.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.projectinter.R

class CustomBaseHeaderView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_base_header_custom_view, this)
    }
}