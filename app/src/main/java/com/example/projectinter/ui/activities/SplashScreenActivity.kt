package com.example.projectinter.ui.activities

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.chipmong.dms.ui.activities.BaseActivity
import com.example.projectinter.R
import com.example.projectinter.utils.JetPackDataStore
import kotlinx.coroutines.launch

class SplashScreenActivity : BaseActivity() {

    private var jetPackDataStore: JetPackDataStore? = null

    override fun isShowHeader(): Boolean {
        return false
    }

    override fun initView() {
        super.initView()
        jetPackDataStore = JetPackDataStore(this)
        if (jetPackDataStore?.exampleConterFlow.toString().isNullOrEmpty()) {
            lifecycleScope.launch {
                jetPackDataStore?.incrementCounter()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            MainActivity.launch(this)
//            DashBoardActivity.launch(this)
        }, 1500)
    }

    override fun layoutContainerRes(): Int = R.layout.activity_splash_screen
}