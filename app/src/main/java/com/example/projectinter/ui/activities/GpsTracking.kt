package com.example.projectinter.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chipmong.dms.constants.LocationSettings
import com.chipmong.dms.constants.RequestCode
import com.chipmong.dms.utils.DmsUtils
import com.chipmong.dms.utils.IntentUitls
import com.example.projectinter.extensions.*
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


class GpsTracking(
    private var activity: AppCompatActivity,
    private var minDistance: Float = LocationSettings.MIN_DISTANCE
) {
    private var mLocationManager: LocationManager? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationTrackerListener: LocationTrackerListener? = null
    private var mCurrentLocation: Location? = null

    init {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mLocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
//            activity.showToast("Location Update Lat : ${location.latitude}, Lng : ${location.longitude}")
            if (DmsUtils.isBetterLocation(location, mCurrentLocation)) {
                locationTrackerListener?.onLocationChanged(location)
                mCurrentLocation = location
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

        }

        override fun onProviderEnabled(provider: String) {
            if (provider == LocationManager.GPS_PROVIDER) {
                alertDialogLocation?.dismiss()
                locationTrackerListener?.onLocationServiceAvailable()
            }
        }

        override fun onProviderDisabled(provider: String) {
            if (provider == LocationManager.GPS_PROVIDER) {
                showDialogLocationSettings()
                locationTrackerListener?.onLocationServiceUnavailable()
            }
        }
    }

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            result?.locations?.let {
                val location = it.last()
                location?.let {
                    if (DmsUtils.isBetterLocation(it, mCurrentLocation)) {
                        locationTrackerListener?.onLocationChanged(it)
                        mCurrentLocation = it
                    }
                }
            }
        }
    }

    private var errDialog: Dialog? = null

    @SuppressLint("MissingPermission")
    fun checkRequestLocationUpdated() {
        if (activity.isBackgroundLocationPermissionGrant()) {
            requestLocationUpdated()
            locationTrackerListener?.onRequestPermissionLocationGrant()
        } else {
//            if (PermissionUtils.shouldShowRequestLocationPermission(activity)) {
//                PermissionUtils.showDialogRequestLocationPermission(activity)
//            } else {

            if (shouldShowRational()) {
                errDialog?.dismiss()
//                Logger.logError("Permission", "Request Permission Deny!!!")
                errDialog =
                    activity.showErrorDialog("We need background location permission", "OK", {
                        activity.goToSettings()
                    })
//                if (activity.isBackgroundLocationPermissionDenied()){
//                    errDialog?.dismiss()
//                    Logger.logError("Permission", "Request Permission Deny!!!")
//                    errDialog =
//                        activity.showErrorDialog("We need background location permission", "OK", {
//                            activity.goToSettings()
//                        })
//                } else{
//                    errDialog?.dismiss()
//                    Logger.logError("Permission", "Request Permission Deny!!!")
//                    errDialog =
//                        activity.showErrorDialog("We need background location permission", "OK", {
//                            activity.goToSettings()
//                        })
//                }
            } else {
//                Logger.logError("Permission", "Request Permission Deny!!!")Deny
                activity.requestLocationPermission()
            }

//            }
        }
    }

    private fun shouldShowRational(): Boolean {
        return if (Build.VERSION.SDK_INT >= 30) {
            activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION) || activity.shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) || activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) || activity.shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else {
                false
            }
        }
    }

    /**
     * Need location permission
     */
    @SuppressLint("MissingPermission")
    private fun requestLocationUpdated() {
        val locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = LocationSettings.MIN_TIME
        locationRequest?.fastestInterval = LocationSettings.FASTEST_INTERVAL
        locationRequest?.smallestDisplacement = minDistance
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            if (isGPSOn()) {
                locationTrackerListener?.onLocationServiceAvailable()
            } else {
                locationTrackerListener?.onLocationServiceUnavailable()
            }

        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
//                        exception.startResolutionForResult(this@HomeActivity,
//                                0)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
        mFusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
        mLocationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, LocationSettings.MAX_TIME,
            LocationSettings.MAX_DISTANCE, mLocationListener
        )
    }

    fun removeAllLocationUpdated() {
        mFusedLocationClient?.removeLocationUpdates(locationCallback)
        mLocationManager?.removeUpdates(mLocationListener)
    }

    fun isGPSOn(): Boolean {
        return mLocationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun getLastKnowLocation(
        onSuccessListener: OnSuccessListener<Location>,
        onFailureListener: OnFailureListener? = null
    ): Boolean {
        if (activity.isBackgroundLocationPermissionGrant()) {
            mFusedLocationClient?.lastLocation?.addOnSuccessListener(onSuccessListener)
            onFailureListener?.let {
                mFusedLocationClient?.lastLocation?.addOnFailureListener(onFailureListener)
            }
            return true
        }
        return false
    }

    @SuppressLint("MissingPermission")
    fun getInstantLastKnowLocation(): Location? {
        return mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun checkGetLastKnowLocation(onSuccessListener: OnSuccessListener<Location>) {
        if (activity.isBackgroundLocationPermissionGrant()) {
            mFusedLocationClient?.lastLocation?.addOnSuccessListener(onSuccessListener)
        } else {
//            if (PermissionUtils.shouldShowRequestLocationPermission(activity)) {
//                PermissionUtils.showDialogRequestLocationPermission(activity, PermissionUtils.LAST_KNOWN_LOCATION_REQUEST_CODE)
//            } else {
            activity.requestLocationPermission()
//            }
        }
    }

    fun onPermissionGrant() {
        requestLocationUpdated()
    }

    fun setLocationTrackerListener(locationTrackerListener: LocationTrackerListener) {
        this.locationTrackerListener = locationTrackerListener
    }

    fun getLocationTrackerListerner(): LocationTrackerListener? {
        return locationTrackerListener
    }

    private var alertDialogLocation: Dialog? = null
    private fun showDialogLocationSettings(): Dialog? {
        alertDialogLocation?.dismiss()
        alertDialogLocation = activity.showAlertDialog(
            "GPS",
            "GPS is off. Please, turn on GPS.",
            "OK",
            null,
            {
                IntentUitls.getToLocationSettings(activity, RequestCode.GPS_TURN_ON)
            })
        alertDialogLocation?.setCancelable(false)
        if (!activity.isDestroyed)
            alertDialogLocation?.show()
        return alertDialogLocation
    }

    interface LocationTrackerListener {
        fun onRequestPermissionLocationGrant() {}
        fun onRequestPermissionLocationDenied() {}
        fun onRequestLastKnownLocationGrant() {}
        fun onLocationServiceUnavailable() {}
        fun onLocationServiceAvailable() {}
        fun onLocationChanged(location: Location) {}
    }
}