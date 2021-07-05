package com.example.projectinter.extensions

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.chipmong.dms.constants.DateFormat
import com.chipmong.dms.constants.RequestCode
import com.chipmong.dms.ui.activities.BaseActivity
import com.chipmong.dms.utils.DateTimeUtils
import com.chipmong.dms.utils.DmsSharePreference
import com.chipmong.dms.utils.DmsUtils
import com.example.projectinter.R
import com.example.projectinter.ui.dialog.CustomAlertDialog
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 04-Feb-20
 */

/**
 * Get status bar height
 */
fun Activity.getStatusBarHeight(): Int {
    return DmsUtils.getStatusBarHeight(this@getStatusBarHeight)
}

@RequiresApi(Build.VERSION_CODES.M)
fun Activity.requestPermission(permission: String, requestCode: Int) {
    this@requestPermission.requestPermissions(arrayOf(permission), requestCode)
}

@RequiresApi(Build.VERSION_CODES.M)
fun Activity.requestPermission(permission: Array<String>, requestCode: Int) {
    this@requestPermission.requestPermissions(permission, requestCode)
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.isPermissionGrant(permission: String): Boolean {
    return this@isPermissionGrant.checkSelfPermission(permission) == PERMISSION_GRANTED
}

fun Context.showToast(message: String): Toast {
    val toast = Toast.makeText(this@showToast, message, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Activity.requestLocationPermission(requestCode: Int = RequestCode.LOCATION_PERMISSION) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermission(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                requestCode
            )
        } else {
            requestPermission(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestCode
            )
        }

    }
}


fun Activity.checkLocationBackgroundDeny(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_DENIED
    } else {
        true
    }
}


@TargetApi(30)
fun Activity.checkBackgroundLocationPermissionAPI30(backgroundLocationRequestCode: Int = RequestCode.LOCATION_PERMISSION) {
    if (checkSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) return
    AlertDialog.Builder(this)
        .setTitle("ACCESS_BACKGROUND_LOCATION")
        .setMessage("checkBackgroundLocationPermissionAPI30")
        .setPositiveButton("YES") { _, _ ->
            // this request will take user to Application's Setting page
            requestPermission(
                arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                backgroundLocationRequestCode
            )
        }
        .setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()

}

fun Context.checkSinglePermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED
}

fun Context.isBackgroundLocationPermissionGrant(): Boolean {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        true
    } else isPermissionGrant(Manifest.permission.ACCESS_FINE_LOCATION) && isPermissionGrant(Manifest.permission.ACCESS_COARSE_LOCATION) && (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) isPermissionGrant(
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    ) else true
            )
}

fun Context.isBackgroundLocationPermissionDenied(): Boolean {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED || this.checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED || this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_DENIED
        } else {
            false
        }
    } else {
        return false
    }
}

fun Context.isLocationPermissionGrant(): Boolean {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        true
    } else isPermissionGrant(Manifest.permission.ACCESS_FINE_LOCATION) && isPermissionGrant(
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
}


private var mErrorDialog: Dialog? = null
fun Context.showErrorDialog(
    message: String,
    title: String = getString(R.string.app_name),
    onClickDone: ((Any?) -> Unit)? = null,
    onClickCancel: ((Any?) -> Unit)? = null
): Dialog? {
    try {
        mErrorDialog?.dismiss()
    } catch (e: Exception) {
        e.printStackTrace()
    }
//    val mBuilder = AlertDialog.Builder(this)
//    mBuilder.setMessage(message)
//    mBuilder.setTitle(title)
//    mBuilder.setPositiveButton(getString(R.string.title_ok), onButtonClick)
//    mBuilder.setCancelable(false)
//
//    mErrorDialog = mBuilder.create()

    mErrorDialog = CustomAlertDialog(this).apply {
        this.setMessage(message)
        this.setNegativeText("Yes")
        this.setPositiveText("No")
        if (onClickCancel == null)
            this.hideNegativeButton()
        this.onClickDone = onClickDone
    }
    mErrorDialog?.show()
    return mErrorDialog
}

fun Context.showAlertDialog(
    title: String,
    message: String,
    positive: String? = "OK",
    negative: String? = "Cancle",
    onClickDone: ((Any?) -> (Unit))? = null,
    onClickCancel: ((Any?) -> (Unit))? = null,
    cancelAble: Boolean = true
): Dialog? {
//    val mBuilder = AlertDialog.Builder(this)
//    mBuilder.setMessage(message)
//    mBuilder.setTitle(title)
//    mBuilder.setPositiveButton(positive, onPositiveButtonClick)
//    mBuilder.setNegativeButton(negative, onNegativeButtonClick)
//    val mErrorDialog = mBuilder.create()
    mErrorDialog = CustomAlertDialog(this).apply {
        setMessage(message)
        setNegativeText(negative ?: "OK")
        setPositiveText(positive ?: "Cancle")
        this.onClickDone = onClickDone
        this.onClickCancel = onClickCancel
        if (onClickCancel == null) {
            this.hideNegativeButton()
        }
        this.setCancelable(cancelAble)
    }
    mErrorDialog?.show()
    return mErrorDialog
}

/**
 *
 */
//fun Activity.isLogin(): Boolean {
//    return DmsSharePreference.getInstance(this).isLoggedIn()
//}


fun Intent.addNewTaskClearTop() {
    this.apply {
        this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
}


/**
 * Get token from share preference
 */
//fun Context.getAccessToken(): String? {
//    return DmsSharePreference.getInstance(this).getAccessToken() ?: ""
//}

fun Activity.requestReadStoragePermission(requestCode: Int = RequestCode.READ_STORAGE_PERMISSION) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermission(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            requestCode
        )
    }
}

fun Activity.isReadExternalPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this@isReadExternalPermission.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED
    } else {
        true
    }
}

fun Activity.shouldShowRequestReadPermissionRationale(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this@shouldShowRequestReadPermissionRationale.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    } else {
        false
    }
}

fun Activity.shouldShowRequestReadPermissionRationaleLocation(): Boolean {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        this@shouldShowRequestReadPermissionRationaleLocation.shouldShowRequestPermissionRationale(
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    } else {
        false
    }
}


fun Activity.isCameraAndStoragePermissionGrant(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this@isCameraAndStoragePermissionGrant.checkSelfPermission(Manifest.permission.CAMERA) == PERMISSION_GRANTED && this@isCameraAndStoragePermissionGrant.checkSelfPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PERMISSION_GRANTED
    } else {
        true
    }
}

fun Activity.requestCameraAndStoragePermission(requestCode: Int = RequestCode.TAKE_PHOTO) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermission(
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            requestCode
        )
    }
}

fun Activity.shouldShowRequestCameraPermissionRationale(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this@shouldShowRequestCameraPermissionRationale.shouldShowRequestPermissionRationale(
            Manifest.permission.CAMERA
        ) && this@shouldShowRequestCameraPermissionRationale.shouldShowRequestPermissionRationale(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    } else {
        false
    }
}

fun BaseActivity.showSnackBar(
    view: View,
    message: String,
    actionMessage: String? = null,
    action: View.OnClickListener? = null
) {
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snackBar.setAction(actionMessage, action)
    snackBar.show()
}

fun Activity.hideKeyBoard() {
    val view = this.findViewById<View>(android.R.id.content)
    if (view != null) {
        val inm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

//fun Activity.clearSession() {
//    DmsSharePreference.getInstance(this@clearSession).clear(this)
//    LoginActivity.launchClearTop(this@clearSession)
//    try {
////        RealmController.deleteAll(Realm.getDefaultInstance())
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}

fun Activity.isKeyBoardShow(): Boolean {
    val imm =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    return imm.isAcceptingText
}

fun Activity.showProgressDialog(): ProgressDialog {
    val progress = ProgressDialog(this)
//    progress.setMessage(getString(R.string.synchronizing_data))
    progress.setCancelable(false)
    progress.show()
    return progress
}

//fun Activity.showDownloadProgressDialog(): CustomProgressDialog {
//    val progress = CustomProgressDialog(this)
//    progress.setMessage(getString(R.string.synchronizing_data))
//    progress.setCancelable(false)
//    progress.show()
//    return progress
//}

/**
 * Get user from share preference
 */
//fun Context.getUser(): UserLoginModel? {
//    return DmsSharePreference.getInstance(this).getUserModel()
//}
//
//fun Context.isKh(): Boolean {
//    return DmsSharePreference.getInstance(this).getSelectedLanguage()
//}
//
//fun Context.typeFaceBold(): Typeface? {
//    return ResourcesCompat.getFont(
//        this,
//        if (this.isKh()) R.font.battambang_bold else R.font.gotham_bold
//    )
//}
//
//fun Context.dmsTypeFace(): Typeface? {
//    return ResourcesCompat.getFont(
//        this,
//        if (this.isKh()) R.font.battambang_regular else R.font.gotham_book
//    )
//}

fun Long.convertDateFromMillisToUTC(): String {
    return DateTimeUtils.convertDateFromMillisToUTC(
        this,
        DateFormat.DATE_FORMAT_4
    )
}

fun Context.holeRadius(): Float {
    return DmsUtils.dp2Pixel(this, 27)
}


//fun Context.isOrderTypeDirectSale(): Boolean {
//    return DmsSharePreference.getInstance(this).isDirectSale(this)
//}
//
//
//fun Context.isOrderTypePreSale(): Boolean {
//    return DmsSharePreference.getInstance(this).isPreSale(this)
//}
//
//
//fun Context.isOrderTypeBoth(): Boolean {
//    return DmsSharePreference.getInstance(this).isBothOrderType(this)
//}

fun Activity.showRationale(permission: String?): Boolean {
    return if (permission != null) {
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
    } else true
}

fun Activity.showAlertDialog(
    title: String?,
    message: String?,
    positiveButtonText: String?,
    negativeButtonText: String?,
    neutralButtonText: String?,
    isCancelable: Boolean,
    listener: DialogInterface.OnClickListener?
): AlertDialog? {
    val dialogBuilder = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(isCancelable)
    if (positiveButtonText != null && !positiveButtonText.isEmpty()) dialogBuilder.setPositiveButton(
        positiveButtonText,
        listener
    )
    if (negativeButtonText != null && !negativeButtonText.isEmpty()) dialogBuilder.setNegativeButton(
        negativeButtonText,
        listener
    )
    if (neutralButtonText != null && !neutralButtonText.isEmpty()) dialogBuilder.setNeutralButton(
        neutralButtonText,
        listener
    )
    return dialogBuilder.show()
}

fun Activity.goToSettings() {
    val intent =
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri =
        Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivityForResult(intent, 0)
}

fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit
) = launch {
    onPreExecute() // runs in Main Thread
    val result = withContext(Dispatchers.IO) {
        doInBackground() // runs in background thread without blocking the Main Thread
    }
    onPostExecute(result) // runs in Main Thread
}


