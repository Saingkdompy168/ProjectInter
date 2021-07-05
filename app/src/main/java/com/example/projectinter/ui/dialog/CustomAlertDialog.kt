package com.example.projectinter.ui.dialog

import android.content.Context
import com.chipmong.dms.extensions.hide
import com.chipmong.dms.ui.dialog.CustomDialog
import com.example.projectinter.R
import kotlinx.android.synthetic.main.both_button_view.view.*
import kotlinx.android.synthetic.main.dialog_check_out.*
import kotlinx.android.synthetic.main.dialog_check_out.view_button

open class CustomAlertDialog(context: Context) : CustomDialog(context) {

    var onClickDone : ((Any?) -> (Unit))? = null
    var onClickCancel : ((Any?) -> (Unit))? = null

    init {
        setContentView(R.layout.dialog_check_out)
        view_button.buttonNo = {
            dismiss()
            onClickCancel?.invoke("")
        }
        view_button.buttonYes = {
           onDoneClick()
        }

        text_view_title.text = textMessage()
    }

    protected open fun onDoneClick(){
        this.onClickDone?.invoke("")
        dismiss()
    }

    protected open fun textMessage() : String?{
        return context.getString(R.string.app_name)
    }

    override fun dialogWidth(): Int {
        return  (context.resources.displayMetrics.widthPixels * 0.6).toInt()
    }

    fun setMessage(message : String){
        text_view_title?.text = message
    }

    fun setNegativeText(text : String){
        view_button?.setLeftButtonText(text)
    }

    fun setPositiveText(text : String){
        view_button?.setRightButtonText(text)
    }

    fun hideNegativeButton(){
        view_button?.button_no?.hide()
    }

}