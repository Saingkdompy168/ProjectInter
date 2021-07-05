package com.chipmong.dms.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import com.chipmong.dms.callback.OnlineOfflineDataRetriever
import com.example.projectinter.R
import kotlinx.android.synthetic.main.dialog_base.*

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 06-Feb-20
 */
abstract class BaseDmsDialog(context: AppCompatActivity, style: Int = R.style.CustomDialog) :
    Dialog(context, style), OnlineOfflineDataRetriever.OnlineOfflineListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_base)

        window!!.setDimAmount(0.5f)
        window!!.setLayout(
            dialogWidth(),
            dialogHeight()
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        windowAnimations()?.let {
            window?.attributes?.windowAnimations = it
        }

        val containerBaseDialog = findViewById<FrameLayout>(R.id.containerBaseDialog)
        containerBaseDialog?.addView(
            LayoutInflater.from(context).inflate(
                layoutContentView(),
                null
            )
        )

        if (this is BaseListDialog<*>) {
            this.initRecyclerView()
        }
        initView()
        initEventListener()

        OnlineOfflineDataRetriever(this).detectInternet()
    }

    protected open fun dialogWidth(): Int {
        return (context.resources.displayMetrics.widthPixels * 0.75).toInt()
    }

    protected open fun dialogHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    abstract fun layoutContentView(): Int

    @StyleRes
    protected open fun windowAnimations() : Int?{
        return R.style.DialogAnimationScaleInOut
    }

    protected open fun initView() {
        dialogToolbarView?.setTitle(title())
        dialogToolbarView?.setTitleTextSize(titleTextSize())
    }

    protected open fun initEventListener() {
        dialogToolbarView?.onCloseClick = {
            dismiss()
        }
    }


    /**
     *
     * Override dialog title
     */
    protected open fun title(): String? {
        return null
    }


    protected open fun showProgressWaiting() {
        progressBar?.visibility = View.VISIBLE
    }

    protected open fun hideProgressWaiting() {
        progressBar?.visibility = View.GONE
    }

    /**
     * Override title text size
     */
    protected open fun titleTextSize(): Float {
        return 16f
    }

    override fun onGetDataOffline() {

    }

    override fun onGetDataOnline() {

    }
}