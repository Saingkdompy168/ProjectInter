package com.example.projectinter.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import com.chipmong.dms.utils.DmsUtils
import com.chipmong.dms.utils.setSafeOnClickListener
import com.example.projectinter.R
import com.example.projectinter.extensions.isLocationPermissionGrant
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : BaseMapActivity(), GoogleMap.OnCameraIdleListener {

    private var mCurrentLatLng: LatLng? = null

    companion object {
        fun launch(context: Activity, latLng: LatLng? = null) {
            context.startActivityForResult(Intent(context, MapsActivity::class.java).apply {
                latLng?.let {
                    putExtra("latLng", latLng)
                }
            },11)
        }
    }

    override fun layoutContainerRes(): Int = R.layout.activity_maps
    override fun toolBarTitle(): String? {
        return "Map"
    }

    override fun isDarkStatus(): Boolean {
        return false
    }

    override fun initView() {
        super.initView()
        if (intent.hasExtra("latlng")) {
            mCurrentLatLng = intent.getParcelableExtra("latlng")
        }
    }

    override fun initEventListener() {
        super.initEventListener()
        btn_done?.setSafeOnClickListener {
            if (mCurrentLatLng != null) {
                val intent = Intent()
                intent.putExtra("lat", mCurrentLatLng?.latitude)
                intent.putExtra("lng", mCurrentLatLng?.longitude)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                onBackPressed()
            }
        }
    }

    override fun isRequireLocationTracking(): Boolean {
        return true
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady() {
        if (isLocationPermissionGrant()) {
            mMap?.isMyLocationEnabled = true
        }
        val top = DmsUtils.dp2Pixel(this, 100).toInt()
        mMap?.setPadding(0, top, 0, 0)
        mMap?.setMinZoomPreference(8f)
        if (mCurrentLatLng == null) {
            getCurrentLocation({ p0 ->
                p0?.let {
                    mCurrentLatLng = LatLng(p0.latitude, p0.longitude)
                    bindLatLngText()
                    mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, 20f))
                }
            })
        } else {
            bindLatLngText()
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, 20f))
        }

        mMap?.setOnCameraIdleListener(this)
    }


    override fun onCameraIdle() {
        mCurrentLatLng = getLatLangFromMap()
        bindLatLngText()
    }

    private fun getLatLangFromMap(): LatLng? {
        return mMap?.cameraPosition?.target
    }

    @SuppressLint("SetTextI18n")
    fun bindLatLngText() {
        mCurrentLatLng?.let {
            tv_address?.text =
                "Address: ${DmsUtils.getCompleteAddressString(this, it.latitude, it.longitude)}"
            tv_lat_value?.text = ": ${it.latitude}"
            tv_lng_value?.text = ": ${it.longitude}"
        }
    }


}