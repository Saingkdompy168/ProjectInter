package com.chipmong.dms.extensions

import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 04-Feb-20
 */

fun ViewGroup.inflate(@LayoutRes res: Int) : View {
   return LayoutInflater.from(this.context).inflate(res, this, false)
}

fun ViewGroup.show(){
   this@show.visibility = View.VISIBLE
}

fun ViewGroup.hide(){
   this@hide.visibility = View.GONE
}

fun ViewGroup.invisible(){
   this@invisible.visibility = View.INVISIBLE
}

fun EditText.setMaxLength( length : Int){
   this@setMaxLength.filters = arrayOf<InputFilter>(LengthFilter(length))
}