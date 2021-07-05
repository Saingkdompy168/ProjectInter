package com.chipmong.dms.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.chipmong.dms.callback.OnlineOfflineDataRetriever
import com.example.projectinter.R
import kotlinx.android.synthetic.main.dialog_base.*

/**
 *
 *
 * @author Noel
 * @version
 * @created on 05-Sep-18
 */
abstract class BaseDialogFragment : DialogFragment(), OnlineOfflineDataRetriever.OnlineOfflineListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomDialog)
        isCancelable = false
    }


    override fun isCancelable(): Boolean {
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.dialog_base, container, false)
        val containerBaseDialog = mView?.findViewById<FrameLayout>(R.id.containerBaseDialog)
        containerBaseDialog?.addView(inflater.inflate(layoutContainerRes(), null))
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        initSubBaseView()
        initView()
        initEventListener()

        OnlineOfflineDataRetriever(this).detectInternet()
    }

    /**
     * Override for some class that need
     */
    protected open fun initSubBaseView(){

    }


    /**
     * Layout container fragment
     * Return layout id
     */
    abstract fun layoutContainerRes(): Int

    open fun initView() {
        dialogToolbarView?.setTitle(title())
    }

    open fun initEventListener() {
        dialogToolbarView?.onCloseClick = {
            dismiss()
        }
    }

    /**
     * Override dialog title
     */
    protected open fun title(): String? {
        return null
    }

    /**
     * Show progress waiting while request data
     */
    protected fun showProgressWaiting() {
        progressBar?.visibility = View.VISIBLE
    }

    /**
     * Hide progress waiting when request data done
     */
    protected fun hideProgressWaiting() {
        progressBar?.visibility = View.GONE
    }

    override fun onGetDataOffline() {

    }

    override fun onGetDataOnline() {

    }
}