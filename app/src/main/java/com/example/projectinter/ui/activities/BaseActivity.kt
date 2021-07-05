package com.chipmong.dms.ui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.chipmong.dms.callback.OnlineOfflineDataRetriever
import com.chipmong.dms.constants.RequestCode
import com.chipmong.dms.extensions.inflate
import com.chipmong.dms.services.repository.RequestRestApiManager
import com.chipmong.dms.socket.SocketClient
import com.chipmong.dms.utils.InternetDetectAsync
import com.example.projectinter.R
import com.example.projectinter.extensions.showSnackBar
import com.example.projectinter.ui.activities.GpsTracking
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_tool_bar_title_center.*

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 04-Feb-20
 */
abstract class BaseActivity : AppCompatActivity(), SocketClient.OnSocketClientCallBack,
    OnlineOfflineDataRetriever.OnlineOfflineListener ,GpsTracking.LocationTrackerListener {
    private var mConnectivityManager: ConnectivityManager? = null
    protected var mRealm: Realm? = null
    protected var mCompositeDisposable: CompositeDisposable? = null
    protected var mGpsTracking: GpsTracking? = null
    //    private lateinit var onlineOfflineDataRetriever: OnlineOfflineDataRetriever
    var mCurrentLocation: Location? = null

    private val netWorkCallBack: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                InternetDetectAsync {
                    it?.let {
                        if (it) {
                            //Do when internet available
                            onInternetAvailable()
                        } else {
                            //Do with local data base
                            onInternetUnAvailable()
                        }
                    }
                }.execute()
            }

            override fun onUnavailable() {
                showSnackBar(base_activity_root, "Please check your connnection and try agin...!")
                onInternetUnAvailable()
            }

            override fun onLost(network: Network) {
                showSnackBar(base_activity_root, "Internet lost...!")
            }
        }

    protected open fun onInternetAvailable() {

    }


    protected open fun onInternetUnAvailable() {

    }

    protected open fun isShowHeader(): Boolean {
        return true
    }

    protected open fun isShowFooter(): Boolean {
        return false
    }

    protected open fun showActionButton(): Boolean {
        return false
    }

    protected open fun showButtonHeaderRight(): Boolean {
        return false
    }

    protected open fun actionButtonText(): String? {
        return null
    }

    protected open fun actionButtonIcon(): Int? {
        return null
    }

    protected open fun onActionButtonClick() {

    }

    protected open fun onFilterButtonClick() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        enterAnimation()
        setStatusBarColor(isDarkStatus())
        if (isFullScree()) setFullScreen()

//        progressBarLoading?.indeterminateTintList = ColorStateList.valueOf(progressColor())
        baseToolBar?.visibility = if (isShowHeader()) View.VISIBLE else View.GONE
        baseToolBar?.setToolBarIconRes(toolBarIcon())
        if (showToolbarBackIcon()) baseToolBar?.showToolbarActionIcon() else baseToolBar?.hideToolbarActionIcon()
        if (showActionButton()) {
//            baseToolBar?.showActionButton()
//            baseToolBar?.setActionButtonText(actionButtonText())
//            baseToolBar?.setActionButtonIcon(actionButtonIcon())
        } else {
//            baseToolBar?.hideActionButton()
        }
        textViewActionBarTitle?.text = toolBarTitle()
        textViewActionBarTitle?.setTextColor(toolBarTitleTextColor())
        imageToolbarAction?.visibility =
            if (showToolbarActionIcon()) View.VISIBLE else View.GONE
        imageFooter?.visibility = if (isShowFooter()) View.VISIBLE else View.GONE
        initAddView()

    }

    protected open fun initAddView() {
        addAnInitView()
    }

    protected fun addAnInitView() {
        layout_base_container.addView(layout_base_container.inflate(layoutContainerRes()))
//        layout_tool_bar_root?.setPadding(0, this.getStatusBarHeight(), 0, 0)
//
//        toolbar?.let {
//            setSupportActionBar(it)
//            it.setTitleTextColor(toolBarTitleTextColor())
//            it.title = toolBarTitle()
//            it.setNavigationIcon(toolBarIcon())
//            it.setNavigationOnClickListener {
//                onBackPressed()
//            }
//        }
        hideProgressLoading()

        initSubBaseView()
        initView()
        initEventListener()
    }

    protected open fun toolBarTitle(): String? {
        return null
    }

    @ColorInt
    protected open fun toolBarTitleTextColor(): Int {
        return Color.WHITE
    }

    protected fun setTitle(title: String?) {
//        toolbar?.title = title
        textViewActionBarTitle?.text = title
    }

    protected open fun toolBarIcon(): Int {
        return R.drawable.ic_circle_arrow_back
    }

    protected open fun showToolbarActionIcon(): Boolean {
        return false
    }

    protected open fun showToolbarBackIcon(): Boolean {
        return true
    }

    @ColorInt
    protected open fun progressColor(): Int {
        return ContextCompat.getColor(this, R.color.colorPrimary)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Init necessary resource of child class
     */
    protected open fun initSubBaseView() {}

    protected open fun isRequireLocationTracking(): Boolean = false

    override fun onResume() {
        super.onResume()
        RequestRestApiManager.init(this)


        if (mGpsTracking == null) {
            mGpsTracking = GpsTracking(this, minDistance()).apply {
                setLocationTrackerListener(this@BaseActivity)
            }
        }

        if (isRequireLocationTracking()) {
            mGpsTracking?.checkRequestLocationUpdated()
        }
    }


    protected open fun minDistance(): Float {
        return 10f
    }

    override fun onPause() {
        super.onPause()
//        mConnectivityManager?.unregisterNetworkCallback(netWorkCallBack)
//        mGpsTracking?.removeAllLocationUpdated()
    }

    @LayoutRes
    abstract fun layoutContainerRes(): Int

    protected open fun isDarkStatus(): Boolean {
        return true
    }

    protected open fun isShowBaseHeaderImage(): Boolean = false

    protected open fun initView() {
//        Logger.log("Activity", this.javaClass.simpleName)
//        imageLoading?.let {
//            Glide.with(it).asGif().load(R.drawable.ic_khb_loading).into(it)
//        }
    }

    protected open fun initEventListener() {
        imageToolbarIcon?.setOnClickListener {
            onBackPressed()
        }
        imageToolbarAction?.setOnClickListener {
            onActionToolbarActionClick()
        }

//        btn_action_button?.setOnClickListener {
//            onActionButtonClick()
//        }
//
//        baseToolBar.button_right.buttonFilter = {
//            onFilterButtonClick()
//        }

//        base_activity_root?.viewTreeObserver?.addOnGlobalLayoutListener {
//            val heightDiff = base_activity_root.rootView.height - base_activity_root.height
//            val dp_300 = DmsUtils.dp2Pixel(this, 100)
//            showToast("Diff $heightDiff")
//            if (isKeyBoardShow() && heightDiff >= 150) {
//                showToast("Keyboard show")
//            } else {
//                showToast("Keyboard hide")
//            }
//        }
    }

    protected open fun onActionToolbarActionClick() {

    }

    private fun setStatusBarColor(isLightStatusBar: Boolean) {
        val window = this.window
        if (isLightStatusBar) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        window.statusBarColor = statusBarColor()
    }


    protected open fun isFullScree(): Boolean = false

    protected fun setFullScreen() {
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }


    @ColorInt
    protected open fun statusBarColor(): Int {
        return ContextCompat.getColor(this, android.R.color.transparent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitAnimation()
        finish()
    }

    open fun showProgressLoading() {
//        layoutProgressBarLoading?.show()
//        progressLoadingView?.show()
    }


    fun setProgressTint(@ColorInt color: Int) {
//        progressLoadingView?.setProgressTint(color)
    }

    open fun hideProgressLoading() {
//        layoutProgressBarLoading?.hide()
//        progressLoadingView?.hide()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isPermissionGrant = true
        for (grant in grantResults) {
            if (grant != PackageManager.PERMISSION_GRANTED) {
                isPermissionGrant = false
                break
            }
        }
        if (isPermissionGrant) {
            onPermissionGrant(requestCode)
        } else {
            onPermissionDenied(requestCode)
        }
    }

    protected open fun onPermissionGrant(requestCode: Int) {
        if (requestCode == RequestCode.LOCATION_PERMISSION) {
//            mGpsTracking?.onPermissionGrant()
        }
    }

    /**
     * Handle when permission denied
     */
    protected open fun onPermissionDenied(requestCode: Int) {

    }

    override fun onSocketNotificationListener(event_type: Any, data: Any) {

    }

    /**
     *  Handle when receive message from socket
     */
    override fun onSocketReceiveMessage(data: Any) {

    }

    /**
     * Handle when socket connect
     * @param data
     */
    override fun onSocketConnect(data: Any) {

    }

    /**
     * Handle when socket disconnected
     */
    override fun onSocketDisconnected(data: Any) {

    }

    /**
     * Handle when socket ack callback
     */
    override fun onAckCallBack(vararg args: Any) {

    }

    /**
     * Override animation when enter screen
     */
    protected open fun enterAnimation() {
        activityEnterRightAnimation()
    }

    /**
     * Override animation when exit screen
     */
    protected open fun exitAnimation() {
        activityExitLeftAnimation()
    }

    /**
     * Enter activity when fade in animation
     */
    protected fun activityEnterFadeInAnimation() {
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_hold)
    }

    /**
     * Exit activity when fade out animation
     */
    protected fun activityExitFadeOutAnimation() {
        overridePendingTransition(R.anim.activity_hold, R.anim.activity_fade_out)
    }

    /**
     * Enter activity with slide up animation
     */
    protected fun activityEnterUpAnimation() {
        overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_hold)
    }

    /**
     * Exit activity with slide down animation
     */
    protected fun activityExitDownAnimation() {
        overridePendingTransition(R.anim.activity_hold, R.anim.activity_slide_down)
    }

    /**
     * Enter activity with slide right animation
     */
    protected fun activityEnterRightAnimation() {
        overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left)
    }

    /**
     * Exit activity with slide left animation
     */
    protected fun activityExitLeftAnimation() {
        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_right)
    }

    /**
     * Get current location
     * @param onSuccessListener
     */
    fun getCurrentLocation(
        onSuccessListener: OnSuccessListener<Location>,
        onFailureListener: OnFailureListener? = null
    ): Boolean {
        return mGpsTracking?.getLastKnowLocation(onSuccessListener, onFailureListener) ?: false
    }

    fun getInstantLastKnowLocation(): Location? {
        return mGpsTracking?.getInstantLastKnowLocation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCode.PICK_PHOTO -> onPickPhotoResult(resultCode, data)
            RequestCode.TAKE_PHOTO -> onTakPhotoResult(resultCode, data)
            RequestCode.CROP_PHOTO -> onCropPhotoResult(resultCode, data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /**
     * Handle pick photo result
     * @param resultCode
     * @param data
     */
    protected open fun onPickPhotoResult(resultCode: Int, data: Intent?) {

    }

    /**
     * Handle take photo result
     * @param resultCode
     * @param data
     */
    protected open fun onTakPhotoResult(resultCode: Int, data: Intent?) {

    }

    /**
     * Handle crop photo result
     * @param resultCode
     * @param data
     */
    protected open fun onCropPhotoResult(resultCode: Int, data: Intent?) {

    }

    override fun onDestroy() {
        mConnectivityManager?.unregisterNetworkCallback(netWorkCallBack)
//        mGpsTracking?.removeAllLocationUpdated()
        super.onDestroy()
        mRealm?.let {
            if (it.isInTransaction) {
                it.cancelTransaction()
            }
            it.close()
        }
        mCompositeDisposable?.clear()
    }

    override fun onGetDataOffline() {

    }

    override fun onGetDataOnline() {

    }

//    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(LocaleHelper.setLocale(newBase, "en"))
//    }

    fun getRealm(): Realm? {
        return mRealm
    }

//
//    override fun onLocationChanged(location: Location) {
//        super.onLocationChanged(location)
//    }

//    protected fun startDmsService(action: String? = null) {
////        if (getUser()?.isRSM!=true){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                startForegroundService(Intent(this, DmsService::class.java).apply {
//                    if (action != null) {
//                        setAction(action)
//                    }
//                })
//            } else {
//                startService(Intent(this, DmsService::class.java))
//            }
////        }
//
//    }
//
//    protected fun stopDmsService(){
//        try {
//            stopService(Intent(this, DmsService::class.java))
//        } catch (e : Exception){
//            e.printStackTrace()
//        }
//
//    }

//    private var photoDialog: PhotoViewDialog? = null
//    fun viewPhoto(image: String) {
//        photoDialog?.dismiss()
//        photoDialog = PhotoViewDialog(this, image)
//        photoDialog?.show()
//    }
}