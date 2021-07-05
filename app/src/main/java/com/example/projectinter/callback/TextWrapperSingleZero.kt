package com.chipmong.dms.callback

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class TextWrapperSingleZero(var editText : EditText) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        if (s.toString().length >1 && s.toString().startsWith("0")) {
            val text = s.toString().toLong()
            editText.setText(text.toString())
            editText.setSelection(text.toString().length)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}