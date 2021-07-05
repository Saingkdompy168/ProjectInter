package com.example.projectinter.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.chipmong.dms.ui.activities.BaseActivity
import com.example.projectinter.R
import com.example.projectinter.adapter.HomePagerAdapter
import com.example.projectinter.models.ViewPagerModel
import com.example.projectinter.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_dash_board.*
import java.util.*
import kotlin.collections.ArrayList

class DashBoardActivity : BaseActivity() {

    private var pagerModel = ArrayList<ViewPagerModel>()

    override fun isShowHeader(): Boolean = false

    override fun layoutContainerRes(): Int = R.layout.activity_dash_board

    override fun initView() {
        super.initView()
        view_pager?.isPagerEnable = true
        addItem()
        val adapter = HomePagerAdapter(supportFragmentManager, pagerModel)
        view_pager.adapter = adapter
        dots_indicator.setViewPager(view_pager)
        view_pager.offscreenPageLimit = pagerModel.size

    }

    private fun autoSlide(page: Int) {
        var pageger = page
        val timer: TimerTask = object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    val numPages: Int = view_pager.adapter!!.count
                    pageger = (pageger + 1) % numPages
                    view_pager.currentItem = pageger
                }
            }
        }
        val time = Timer()
        time.schedule(timer, 0, 5000)
    }

    private fun addItem() {
        for (i in 0..3) {
            pagerModel.add(ViewPagerModel().apply {
                fragment = HomeFragment.newInstance()
            })
        }
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, DashBoardActivity::class.java))
        }
    }

}