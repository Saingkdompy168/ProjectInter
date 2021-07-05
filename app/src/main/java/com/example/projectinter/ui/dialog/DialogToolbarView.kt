package com.example.projectinter.ui.dialog

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.projectinter.R
import kotlinx.android.synthetic.main.layout_dialog_title.view.*

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 11-Feb-20
 */
class DialogToolbarView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {
    var onCloseClick: (() -> (Unit))? = null

    private var mTextSize: Float = 16f
    private var mTextTitle: String? = null

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.DialogToolbarView, 0, 0)
            .apply {
                try {
                    if (this.hasValue(R.styleable.DialogToolbarView_textSize)) {
                        mTextSize = this.getFloat(R.styleable.DialogToolbarView_textSize, 16f)
                    }

                    if (this.hasValue(R.styleable.DialogToolbarView_textTitle)){
                        mTextTitle = this.getString(R.styleable.DialogToolbarView_textTitle)
                    }
                } finally {
                    recycle()
                }
            }
        LayoutInflater.from(context).inflate(R.layout.layout_dialog_title, this)
        setTitleTextSize(mTextSize)
        mTextTitle?.let {
            setTitle(it)
        }
        imageViewClose?.setOnClickListener {
            onCloseClick?.invoke()
        }
    }

    fun setTitle(title: String? = null) {
        textViewTitle?.text = title
    }

    fun setTitleTextSize(size: Float) {
        textViewTitle?.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }
}