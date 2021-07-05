package com.example.projectinter.application

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import com.chipmong.dms.constants.Constants
import com.chipmong.dms.services.repository.ApiClient
import com.chipmong.dms.utils.DmsUtils
import io.realm.Realm
import io.realm.RealmConfiguration


class ProjectInterApp : Application() {
    companion object {

    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        //Configuration realm database
        val realmConfiguration =
            RealmConfiguration.Builder().schemaVersion(1).name(Constants.DEFAULT_DATABASE_NAME)
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()

        try {
//            ApiClient.initOkHttp(DmsUtils.getAndroidId(this))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}