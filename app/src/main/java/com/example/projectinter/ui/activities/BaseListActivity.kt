package com.chipmong.dms.ui.activities

import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chipmong.dms.adapter.BaseRecyclerAdapter
import com.chipmong.dms.services.model.ApiDataModel
import com.example.projectinter.R

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 05-Feb-20
 */
abstract class BaseListActivity<T> : BaseActivity(), BaseRecyclerAdapter.OnItemClickListener<T> {


    protected var mRecyclerView: RecyclerView? = null
    protected var mData = ArrayList<T>()
    protected var mLayoutManager: RecyclerView.LayoutManager? = null
    protected var mAdapter: BaseRecyclerAdapter<T>? = null
    protected var mItemDecoration: RecyclerView.ItemDecoration? = null

    //Default init offset
    var offset = 0

    open fun layoutManager(): RecyclerView.LayoutManager{
        return LinearLayoutManager(this)
    }

    abstract fun adapter(): BaseRecyclerAdapter<T>


    protected open fun itemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    protected open fun itemDecorationSize() : Int{
        return   this.resources.getDimensionPixelSize(R.dimen.default_item_decoration_size)
    }

    @AnimRes
    protected open fun layoutAnimation(): Int? {
        return null
    }

    protected open fun limit(): Int = 10

    override fun initSubBaseView() {
        super.initSubBaseView()
        this.initRecyclerView()
    }

    protected open fun initRecyclerView() {
        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        mLayoutManager = layoutManager()
        mItemDecoration = itemDecoration()
mAdapter = adapter()
        mRecyclerView?.apply {
            addOnItemClickListener()
            this.setHasFixedSize(true)
            this.layoutManager = mLayoutManager
            this.adapter = mAdapter

            mItemDecoration?.let {
                this.addItemDecoration(it)
            }

            layoutAnimation()?.let {
                this.layoutAnimation = AnimationUtils.loadLayoutAnimation(this@BaseListActivity, it)
            }

        }
    }

    protected open fun addOnItemClickListener(){
        mRecyclerView?.apply {
            this.addOnItemTouchListener(
                BaseRecyclerAdapter.RecyclerTouchListener(
                    this@BaseListActivity,
                    this,
                    this@BaseListActivity
                )
            )
        }
    }

    override fun initEventListener() {
        super.initEventListener()
        mAdapter?.onLoadMoreItems = {
            loadMore()
//            mAdapter?.isLoading = false
        }
    }

    protected open fun onResponseDataSuccess(data: List<T>) {
        this.offset += data.size
        mAdapter?.apply {
            this.isLoading = false
            this.isLoadAllItemDone = data.isEmpty()
        }
    }

    protected open fun onResponseDataSuccess(data: ApiDataModel<List<T>>) {
        this.offset += data.data?.size?:0
        mAdapter?.apply {
            this.isLoading = false
            this.isLoadAllItemDone = data.data!!.isEmpty()
        }
    }

    protected open fun onResponseDataFailed(t: Throwable) {
        mAdapter?.isLoading = false
    }

    /**
     * Handle load more item if need
     */
    protected open fun loadMore() {

    }

}