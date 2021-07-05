package com.example.projectinter.ui.activities

import com.chipmong.dms.ui.activities.BaseActivity
import com.example.projectinter.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


abstract class BaseMapActivity : BaseActivity(), OnMapReadyCallback {


    //Map object using with child activity
    protected var mMap: GoogleMap? = null

    override fun initSubBaseView() {
        super.initSubBaseView()
        initMap()
    }

    private fun initMap() {
        val mMap = supportFragmentManager.findFragmentById(R.id.map)
        mMap?.apply {
            (this as SupportMapFragment).getMapAsync(this@BaseMapActivity)
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        onMapReady()
    }

    /**
     * Handle when map load ready
     */
    abstract fun onMapReady()

}