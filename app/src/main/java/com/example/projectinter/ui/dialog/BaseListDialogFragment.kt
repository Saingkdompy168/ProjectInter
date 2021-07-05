package com.chipmong.dms.ui.dialog

import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.RecyclerView
import com.chipmong.dms.adapter.BaseRecyclerAdapter
import com.example.projectinter.R
import io.realm.Realm

/**
 *
 *
 * @author Noel
 * @version
 * @created on 05-Sep-18
 */
abstract class BaseListDialogFragment<T> : BaseDialogFragment() {

    protected var mRecyclerView: RecyclerView? = null
    protected lateinit var mAdapter: BaseRecyclerAdapter<T>
    protected lateinit var mLayoutManager: RecyclerView.LayoutManager
    protected var mData = ArrayList<T>()
    protected var isFirstDataLoaded = false
    protected var mRealm: Realm? = null
    private var offset = 0


    abstract fun initLayoutManager(): RecyclerView.LayoutManager

    abstract fun initAdapter(): BaseRecyclerAdapter<T>

    protected open fun itemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    protected open fun layoutAnimationController(): LayoutAnimationController? {
        return null
    }

    override fun initSubBaseView() {
        super.initSubBaseView()
        initRecyclerView()
    }

    //RecyclerView id must be R.id.recyclerView
    private fun initRecyclerView() {
        mAdapter = initAdapter()
        mLayoutManager = initLayoutManager()
        mRecyclerView = view?.findViewById(R.id.recyclerView)
        mRecyclerView?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = mLayoutManager
            this.adapter = mAdapter
            itemDecoration()?.apply {
                mRecyclerView?.addItemDecoration(this)
            }
            layoutAnimationController()?.let {
                mRecyclerView?.layoutAnimation = it
            }
        }
    }


    protected open fun onResponseDataSuccess(data: List<T>) {
        this.offset += data.size
        mAdapter.apply {
//            this.isLoading = false
            this.isLoadAllItemDone = data.isEmpty()
        }
    }

    protected open fun onResponseDataFailed(t: Throwable) {
//        mAdapter.isLoading = false
    }


}