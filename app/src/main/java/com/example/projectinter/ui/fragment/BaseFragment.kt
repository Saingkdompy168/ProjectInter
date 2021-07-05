package com.chipmong.dms.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.chipmong.dms.callback.OnlineOfflineDataRetriever
import com.chipmong.dms.utils.DmsSharePreference
import com.example.projectinter.R

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 04-Feb-20
 */
abstract class BaseFragment : Fragment(), OnlineOfflineDataRetriever.OnlineOfflineListener {

    var mContext: Context? = null
    var isViewCreated : Boolean = false
    var  onViewCreated : (() -> (Unit))?=  null


    @LayoutRes
    abstract fun layoutContainerRes(): Int

    @ColorInt
    protected open fun progressColor(): Int? {
        return if (context != null) ContextCompat.getColor(
            requireContext(),
            R.color.colorPrimary
        ) else null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutContainerRes(), container, false)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        context?.let {
//            mToken = DmsSharePreference.getInstance(it).getAccessToken()
//        }
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        mToken?.let {
//            outState.putString("token", it)
//        }
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        savedInstanceState?.let {
//            if (it.containsKey("token"))
//                mToken = it.getString("token")
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        onViewCreated?.invoke()
        progressColor()?.let {
//            progressBarLoading?.indeterminateTintList = ColorStateList.valueOf(it)
        }
        initSubBaseView()
        initView()
        initEventListener()

        OnlineOfflineDataRetriever(this).detectInternet()


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
//        mContext = context
    }

    protected open fun initSubBaseView() {}

    /**
     * Init necessary view
     */
    protected open fun initView() {
//        imageLoading?.let {
//            Glide.with(it).asGif().load(R.drawable.ic_khb_loading).into(it)
//        }
    }

    /**
     * Init necessary event listener
     */
    protected open fun initEventListener() {

    }

    open fun onInternetAvailable() {

    }

    override fun onGetDataOffline() {

    }

    override fun onGetDataOnline() {

    }

//    fun showProgressLoading(){
//        progressLoadingView?.show()
//    }
//
//    fun hideProgressLoading(){
//        progressLoadingView?.hide()
//    }

}