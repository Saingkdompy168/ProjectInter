package com.chipmong.dms.callback

import android.text.Editable
import android.text.TextWatcher

class TextWrapperWithoutZero : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        if (s.toString().length == 1 && s.toString().startsWith("0")) {
            s?.clear();
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}