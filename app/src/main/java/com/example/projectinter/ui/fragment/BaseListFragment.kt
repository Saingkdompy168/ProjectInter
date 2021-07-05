package com.chipmong.dms.ui.fragment

import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView
import com.chipmong.dms.adapter.BaseRecyclerAdapter
import com.example.projectinter.R

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 05-Feb-20
 */
abstract class BaseListFragment<T> : BaseFragment(), BaseRecyclerAdapter.OnItemClickListener<T> {

    protected var mRecyclerView: RecyclerView? = null
    protected var mData = ArrayList<T>()
    protected var mLayoutManager: RecyclerView.LayoutManager? = null
    protected var mAdapter: BaseRecyclerAdapter<T>? = null
    protected var isInit: Boolean = false
    protected var offset = 0
    protected var mItemDecoration: RecyclerView.ItemDecoration? = null

    abstract fun layoutManager(): RecyclerView.LayoutManager
    abstract fun adapter(): BaseRecyclerAdapter<T>

    protected open fun itemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    protected open fun itemDecorationSize(): Int {
        return this.resources.getDimensionPixelSize(R.dimen.default_item_decoration_size)
    }

    fun getAdapter(): BaseRecyclerAdapter<T>? {
        return mAdapter
    }

    @AnimRes
    protected open fun layoutAnimation(): Int? {
        return null
    }

    protected open fun limit(): Int {
        return 100
    }

    override fun initSubBaseView() {
        super.initSubBaseView()
        initRecyclerView()
    }


    open fun setData(mData: ArrayList<T>) {
        this.mData = mData
    }

    //RecyclerView id must be R.id.recyclerView
    private fun initRecyclerView() {
        mRecyclerView = view?.findViewById(R.id.recyclerView)
        mLayoutManager = layoutManager()
        mAdapter = adapter()
        mAdapter?.onItemClickListener = this

        mRecyclerView?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = mLayoutManager
            this.adapter = mAdapter

            itemDecoration()?.let {
                mItemDecoration = it
                this.addItemDecoration(it)
            }

            layoutAnimation()?.let {
                this.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, it)
            }
        }
        isInit = true
    }

    override fun initEventListener() {
        super.initEventListener()
        mAdapter?.onLoadMoreItems = {
            loadMore()
        }
    }

    protected open fun onResponseDataSuccess(data: List<T>) {
        this.offset += data.size
        mAdapter?.apply {
            this.isLoading = false
            this.isLoadAllItemDone = data.isEmpty()
        }
    }

    protected open fun onResponseDataFailed(t: Throwable) {
        mAdapter?.isLoading = false
    }

    protected open fun loadMore() {

    }

}