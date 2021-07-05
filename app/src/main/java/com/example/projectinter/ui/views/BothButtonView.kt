package com.example.projectinter.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.projectinter.R
import kotlinx.android.synthetic.main.both_button_view.view.*

class BothButtonView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    var buttonYes: (() -> (Unit))? = null
    var buttonNo: (() -> (Unit))? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.both_button_view, this)

        button_no.setOnClickListener {
            buttonNo?.invoke()
        }
        button_yes.setOnClickListener {
            buttonYes?.invoke()
        }

    }

    fun setLeftButtonText(text: String) {
        button_no.text = text
    }

    fun setRightButtonText(text: String) {
        button_yes.text = text
    }
}