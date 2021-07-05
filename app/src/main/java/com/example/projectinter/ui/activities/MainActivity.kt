package com.example.projectinter.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.widget.Toast
import com.chipmong.dms.ui.activities.BaseActivity
import com.chipmong.dms.utils.DmsUtils
import com.example.projectinter.R
import com.example.projectinter.extensions.isBackgroundLocationPermissionGrant
import com.example.projectinter.extensions.requestLocationPermission
import com.example.projectinter.utils.JetPackDataStore
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var jetPackDataStore: JetPackDataStore? = null

    override fun isShowHeader(): Boolean = false

    override fun isDarkStatus(): Boolean = true

    override fun layoutContainerRes(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        jetPackDataStore = JetPackDataStore(this)
        Toast.makeText(
            this,
            "setting Data Store" + jetPackDataStore?.exampleConterFlow,
            Toast.LENGTH_SHORT
        ).show()

        if (this.isBackgroundLocationPermissionGrant()) {
            setLatLng(DmsUtils.getInstantLastKnowLocation(this))
        } else {
            this.requestLocationPermission()
        }

        text_view_pick_up_map.setOnClickListener {
            MapsActivity.launch(this, getLatLng())
        }
    }

    private fun setLatLng(location: Location?) {
        location?.let {
            layoutLongitude?.text = "${location.longitude}"
            layoutLatitude?.text = "${location.latitude}"
        }
    }

    private fun setLatLng(location: LatLng?) {
        location?.let {
            layoutLongitude?.text = "${location.longitude}"
            layoutLatitude?.text = "${location.latitude}"
        }
    }

    fun getLatLng(): LatLng? {
        if (layoutLatitude.text != null && layoutLongitude.text != null) {
            return try {
                LatLng(
                    layoutLatitude.text.toString().toDouble(),
                    layoutLongitude.text.toString().toDouble()
                )
            } catch (e: java.lang.Exception) {
                null
            }
        }
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 11 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("lat") && data.hasExtra("lng")) {
                val latlng =
                    LatLng(data.getDoubleExtra("lat", 0.0), data.getDoubleExtra("lng", 0.0))
                setLatLng(latlng)
            }
        }
    }


    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as Activity).finish()
        }
    }
}