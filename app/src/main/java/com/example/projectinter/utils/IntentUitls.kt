package com.chipmong.dms.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import androidx.core.content.ContextCompat.startActivity


/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 02-Jan-19
 */
object IntentUitls {

    fun goToSettings(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)
    }

    fun goToAppLocationSetting(activity: Activity, requestCode: Int) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//        val uri = Uri.fromParts(
//            "package",
//            BuildConfig.APPLICATION_ID, null
//        )
//        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivityForResult(intent, requestCode)
    }

    fun shareTextToOtherApp(activity: Activity, message: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.type = "text/plain"
        activity.startActivity(sendIntent)
    }

    fun getToLocationSettings(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
            requestCode
        )
    }

    fun composeEmail(
        context: Context,
        addresses: Array<String>,
        subject: String? = null,
        attachment: Uri? = null
    ) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_EMAIL, addresses)
            if (subject != null)
                putExtra(Intent.EXTRA_SUBJECT, subject)
            if (attachment != null)
                putExtra(Intent.EXTRA_STREAM, attachment)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun composeEmail(context: Context, addresses: Array<String>, subject: String? = null) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun setSms(context: Context, phoneNumber: String, body: String? = null) {
        context.startActivity(getSenMessageIntent(phoneNumber, body))
    }

    fun getSenMessageIntent(phoneNumber: String, body: String? = null): Intent {
        val sms_uri = Uri.parse("smsto:$phoneNumber")
        val sms_intent = Intent(Intent.ACTION_SENDTO, sms_uri)
        sms_intent.putExtra("sms_body", body ?: "")
        return sms_intent
    }

    fun selectContact(context: Activity) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivityForResult(intent, 0)
        }
    }

    fun getImageFromGallery(activity: Activity, RESULT_LOAD_IMG: Int) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        activity.startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    fun startDirection(activity: Activity, lat: Double, lng: Double) {
        val gmmIntentUri = Uri.parse("google.navigation:q=$lat,$lng")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(mapIntent)
        }
    }
}