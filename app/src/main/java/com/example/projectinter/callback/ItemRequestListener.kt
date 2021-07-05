package com.chipmong.dms.callback


/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 09-Jul-20
 */
interface ItemRequestListener {
    fun isShowStock() : Boolean

    fun bottomTitle(): String

    fun buttonNextText(): String

    fun onButtonNextClick()
    fun onTotalItemClick()
    fun initTabItems(){}

    fun totalLabel() : String

    fun onDialogNextClick()
}