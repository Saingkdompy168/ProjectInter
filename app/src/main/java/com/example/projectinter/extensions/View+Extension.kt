package com.chipmong.dms.extensions

import android.content.Context
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.projectinter.R
import java.text.DecimalFormat

fun View.setColorBackground(context: Context, id: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, id))
}


fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.checkShow(boolean: Boolean) {
    if (boolean)
        this.visibility = View.GONE
    else this.visibility = View.GONE
}

fun View.fadInFadeOutAnimation(context: Context,boolean: Boolean) = if(boolean){
    val fadeInAnimation: Animation =
        AnimationUtils.loadAnimation(context, R.anim.activity_fade_in)
    this.startAnimation(fadeInAnimation)
}else{
    val fadeInAnimation: Animation =
        AnimationUtils.loadAnimation(context, R.anim.activity_fade_out)
    this.startAnimation(fadeInAnimation)
}

fun Boolean.toInt() = if (this) 1 else 0

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun String.lowerCaseAMAndPM() {
    this.replace("AM", "am").replace("PM", "pm")
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun Double.format(fracDigits: Int): String {
    val df = DecimalFormat()
    df.maximumFractionDigits = fracDigits
    return df.format(this)
}