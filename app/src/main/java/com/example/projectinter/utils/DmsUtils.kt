package com.chipmong.dms.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.media.ExifInterface
import android.provider.Settings
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.format.DateUtils
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import java.io.File
import java.text.DecimalFormat
import java.util.*
import kotlin.math.roundToInt


/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 04-Feb-20
 */
object DmsUtils {

    fun getScreenWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun dp2Pixel(context: Context, dp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        )
    }

    fun sp2Pixel(context: Context, dp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dp.toFloat(), context.resources.displayMetrics
        )
    }

    fun px2Dp(context: Context, px: Int): Int {
        return (px / context.resources.displayMetrics.density).toInt()
    }

    fun px2Sp(context: Context, px: Int): Float {
        return px / context.resources.displayMetrics.scaledDensity
    }

    fun isBetterLocation(location: Location, currentBestLocation: Location? = null): Boolean {
//        val TWO_MINUTES = 2 * DateUtils.MINUTE_IN_MILLIS
        val TEN_SECONDS = 30 * DateUtils.SECOND_IN_MILLIS
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true
        }

        // Check whether the new location fix is newer or older
        val timeDelta = location.time - currentBestLocation.time
        val isSignificantlyNewer = timeDelta > TEN_SECONDS
        val isSignificantlyOlder = timeDelta < -TEN_SECONDS
        val isNewer = timeDelta > 0

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false
        }

        // Check whether the new location fix is more or less accurate
        val accuracyDelta = location.accuracy - currentBestLocation.accuracy
        val isLessAccurate = accuracyDelta > 0
        val isMoreAccurate = accuracyDelta < 0
//        val isSignificantlyLessAccurate = accuracyDelta > 200
        val isSignificantlyLessAccurate = accuracyDelta > 100

        // Check if the old and new location are from the same provider
        val isFromSameProvider = isSameProvider(
            location.provider,
            currentBestLocation.provider
        )

        // Determine location quality using a combination of timeliness and accuracy
        return when {
            isMoreAccurate -> true
            isNewer && !isLessAccurate -> true
            else -> isNewer && !isSignificantlyLessAccurate && isFromSameProvider
        }
    }

    private fun isSameProvider(provider: String, provider1: String): Boolean {
        return provider == provider1
    }

    fun getSmallMarkerBitmap(context: Context, @DrawableRes icon: Int): Bitmap {
        val originBitmap = BitmapFactory.decodeResource(context.resources, icon)
        return Bitmap.createScaledBitmap(originBitmap, 100, 100, false)
    }

//    fun isNearBy(current: LatLng, dest: LatLng, radius: Double = 1000.0): Boolean {
//        return getDistance(current, dest) <= radius
//    }
//
//
//    fun isInside(current: LatLng, dest: LatLng, radius: Double = 1000.0): Boolean {
//        return getDistance(current, dest) <= radius
//    }
//
//    fun getDistance(center: LatLng, current: LatLng): Double {
//        return SphericalUtil.computeDistanceBetween(
//            center,
//            current
//        )
//    }

    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, id: Int) {
//        val manager = fragmentManager
//        val transaction = manager.beginTransaction()
//        transaction.add(id, fragment)
//        transaction.commit()
        val fm: FragmentManager = fragmentManager
        val ft = fm.beginTransaction()
        ft.replace(id, fragment)
        ft.addToBackStack(null)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()

    }

    fun replaceFragmentWithBackStack(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        id: Int
    ) {
        val fm: FragmentManager = fragmentManager
        val ft = fm.beginTransaction()
        ft.replace(id, fragment)
        ft.addToBackStack(null)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commitAllowingStateLoss()

    }

    fun getDisplayMetrics(context: Activity): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        context.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)

        return displayMetrics
    }


    fun getShortName(name: String?): String {
        var short = ""
        name?.let {
            val spitString = name.split(" ")
            if (spitString.isNotEmpty()) {
                for (text in spitString) {
                    if (short.length < 2) {
                        if (text.isNotEmpty())
                            short += text[0].toUpperCase()
                    } else {
                        break
                    }
                }
            }
        }
        return short
    }

    fun setTextWithApostrose(textView: TextView, text: String, isApostropheMode: Boolean = true) {
        if (text.contains("*")) {
            if (isApostropheMode) {
                val stringBuilder = SpannableStringBuilder(text)
                val start = text.indexOf("*")
                stringBuilder.setSpan(
                    ForegroundColorSpan(Color.RED),
                    start,
                    text.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                textView.text = stringBuilder
            } else {
                val newText = text.replace("*", "")
                newText.trim()
                textView.text = newText
            }
        } else {
            textView.text = text
        }
    }

    @SuppressLint("MissingPermission")
    fun getInstantLastKnowLocation(context: Context): Location? {
        val mLocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }

    fun hideKeyBoard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus;
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity);
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0);
    }

    fun hideKeyBoard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        imm.hideSoftInputFromWindow(view.windowToken, 0);
    }

    fun showKeyBoard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


    fun isExpiredToken(code: Int, message: String = ""): Boolean {
        return (code == 403 || code == 400) && (message.contains(
            "Account have been login in another device",
            true
        ) || message.contains("Account has been login in another device", true) || message.contains(
            "Your account has been logged in from another device",
            true
        ))
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        );
    }

    fun convertNumber(d: Double): String {
        if (d >= 1000) {
            return "${DmsUtils.priceDecimal().format(d / 1000)}k"
        } else {
            return DmsUtils.priceDecimal().format(d)
        }
    }


    fun getFloatDimension(context: Context, @DimenRes resource: Int): Float {
        val typedValue = TypedValue();
        context.resources.getValue(resource, typedValue, true);
        return typedValue.float
    }

    fun resizeImage(file: File, size: Int = 1024) {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        val isLandScape = photoW > photoH
        // Determine how much to scale down the image
        val scaleFactor =
            if (isLandScape) (photoW / size).coerceAtMost(photoH / size) else 1

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        val resized = BitmapFactory.decodeFile(file.absolutePath, bmOptions) ?: return
        file.outputStream().use {
            resized.compress(Bitmap.CompressFormat.JPEG, 90, it)
            resized.recycle()
        }
    }

    fun scaleDown(
        file: File, maxImageSize: Float = 1024f,
        filter: Boolean = true
    ) {
        try {
            val exif = ExifInterface(file.getAbsolutePath());
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            val matrix = Matrix()
            when (orientation) {
                6 -> {
                    matrix.postRotate(90f);
                }
                3 -> {
                    matrix.postRotate(180f);
                }
                8 -> {
                    matrix.postRotate(270f);
                }
                // rotating bitmap
            }
            val originBitMap = BitmapFactory.decodeFile(file.absolutePath)
            val orientationBitmap = Bitmap.createBitmap(
                originBitMap,
                0,
                0,
                originBitMap.width,
                originBitMap.height,
                matrix,
                true
            )
            val ratio =
                (maxImageSize / orientationBitmap.width).coerceAtMost(maxImageSize / orientationBitmap.height)
            val width = (ratio * orientationBitmap.width).roundToInt()
            val height = (ratio * orientationBitmap.height).roundToInt()
            val resized = Bitmap.createScaledBitmap(
                orientationBitmap, width,
                height, filter
            )
            file.outputStream().use {
                resized.compress(Bitmap.CompressFormat.JPEG, 90, it)
                resized.recycle()
            }
        } catch (e: Exception) {

        }
    }

    /**
     *
     */
    fun convertPhoneNumber(number: String?): String? {
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        return try {
            val phoneNumber = phoneNumberUtil.parse(number, "KHM")
            phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL).trim()
                .replace(" ", "")
        } catch (e: java.lang.Exception) {
            ""
        }
    }

    fun isValidatePhone(number: String): Boolean {
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        return try {
            if (number.startsWith("+")) {
                val phoneNumber = phoneNumberUtil.parse(number, "KHM")
                phoneNumberUtil.isValidNumber(phoneNumber)
            } else {
                val phoneNumber = Phonenumber.PhoneNumber()
                phoneNumber.countryCode = 855
                phoneNumber.nationalNumber = number.toLong()
                phoneNumberUtil.isValidNumber(phoneNumber)
            }
        } catch (e: java.lang.Exception) {
            false
        }
    }

//    @SuppressLint("MissingPermission")
//    fun getLastKnownLocation(context: Context): Location? {
//        return if (context.isBackgroundLocationPermissionGrant()) {
//            val mLocationManager =
//                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//        } else {
//            null
//        }
//    }

    fun decimal(): DecimalFormat {
        return DecimalFormat("0.00")
    }

    fun decimal(d: Double): String? {
        val df = DecimalFormat("#.##")
        return df.format(d)
    }


    fun convertPhoneKhNational(phone: String?): String? {
        var phoneNational: String? = phone
        phone?.let {
            if (phone.startsWith("+855")) {
                phoneNational = phone.replace("+855", "0")
            }
        }
        return phoneNational
    }

    fun setSpannableTextColor(
        context: Context,
        myText: String,
        textColor: Int
    ): SpannableString {
        val spannableContent = SpannableString(myText)
        spannableContent.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, textColor)),
            3,
            5,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableContent.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, textColor)),
            10,
            13,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        return spannableContent
    }

    fun priceDecimal(): DecimalFormat {
        return DecimalFormat("#,###,##0.00")
    }

    fun qtyDecimal(): DecimalFormat {
        return DecimalFormat("#,###,###")
    }

    fun setStrokeLine(textView: TextView) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    fun getZoomLevel(radius: Double, width: Int = 500): Float {
        val scale = radius / width
        return (16 - Math.log(scale) / Math.log(2.0)).toInt().toFloat()
    }

    fun mileToMeets(mile: Double): Double {
        return mile * 1609.344
    }

    fun metreToMile(metre: Double): Double {
        return metre / 1609.344
    }

    fun getCompleteAddressString(context: Context, LATITUDE: Double, LONGITUDE: Double): String {
        var strAdd = "Unnamed Road"
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (!addresses.isNullOrEmpty()) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = StringBuilder("")

                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i))
                        .append(if (i != returnedAddress.maxAddressLineIndex) "\n" else "")
                }
                strAdd = strReturnedAddress.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return strAdd
    }

//    fun isInsidePlace(center: LatLng, current: LatLng, radius: Double): Boolean {
//        return getDistance(center, current) <= radius
//    }

    fun disableEmojiInTitle(editText: EditText) {
        val emojiFilter = InputFilter { source, start, end, dest, dstart, dend ->
            for (index in start until end) {
                val type = Character.getType(source[index])

                when (type) {
                    '*'.toInt(),
                    Character.OTHER_SYMBOL.toInt(),
                    Character.SURROGATE.toInt() -> {
                        return@InputFilter ""
                    }
                    Character.LOWERCASE_LETTER.toInt() -> {
                        val index2 = index + 1
                        if (index2 < end && Character.getType(source[index + 1]) == Character.NON_SPACING_MARK.toInt())
                            return@InputFilter ""
                    }
                    Character.DECIMAL_DIGIT_NUMBER.toInt() -> {
                        val index2 = index + 1
                        val index3 = index + 2
                        if (index2 < end && index3 < end &&
                            Character.getType(source[index2]) == Character.NON_SPACING_MARK.toInt() &&
                            Character.getType(source[index3]) == Character.ENCLOSING_MARK.toInt()
                        )
                            return@InputFilter ""
                    }
                    Character.OTHER_PUNCTUATION.toInt() -> {
                        val index2 = index + 1

                        if (index2 < end && Character.getType(source[index2]) == Character.NON_SPACING_MARK.toInt()) {
                            return@InputFilter ""
                        }
                    }
                    Character.MATH_SYMBOL.toInt() -> {
                        val index2 = index + 1
                        if (index2 < end && Character.getType(source[index2]) == Character.NON_SPACING_MARK.toInt())
                            return@InputFilter ""
                    }
                }
            }
            return@InputFilter null
        }
        editText.filters = arrayOf(emojiFilter)
    }

    fun getDashboardColor(position: Int): String {
        val colors = arrayOf(
            "#A3061F", "#D6102F", "#FF333B", "#FF4149", "#FF5057",
            "#FF6167", "#FF6C72", "#FB777C", "#FF8388", "#F88C91"
        )
        return if (position > 9) colors[9] else colors[position]
    }

    fun getPercentage(value1: Float, value2: Float): Int {
        return when {
            value2 == 0f -> 0
//            value1 > value2 -> 100
            else -> ((value1 / value2) * 100).roundToInt()
        }
    }
    

    fun removeCommaLastIndex(oldValue: java.lang.StringBuilder, textValue: EditText) {
        if (oldValue.endsWith(" , ")) {
            val data = oldValue.substring(0, oldValue.lastIndexOf(" , "))
            textValue.append(data.toString())
        }
    }
}