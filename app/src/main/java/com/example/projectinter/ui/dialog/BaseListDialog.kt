package com.chipmong.dms.ui.dialog

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.chipmong.dms.adapter.BaseRecyclerAdapter
import com.example.projectinter.R

/**
 *
 *
 * @author Pathmazing
 * @version
 * @created on 10-Feb-20
 */
abstract class BaseListDialog<T>(private var context: AppCompatActivity, style: Int) :
    BaseDmsDialog(context, style), BaseRecyclerAdapter.OnItemClickListener<T> {

    protected var mData = ArrayList<T>()
    private var mRecycler: RecyclerView? = null
    protected var mAdapter: BaseRecyclerAdapter<T>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var offset = 0

    abstract fun adapter(): BaseRecyclerAdapter<T>
    abstract fun layoutManager(): RecyclerView.LayoutManager

    //RecyclerView id must be R.id.recyclerView
    fun initRecyclerView() {
        mRecycler = findViewById(R.id.recyclerView)
        mAdapter = adapter()
        mLayoutManager = layoutManager()
        mRecycler?.apply {
            this.setHasFixedSize(true)
            this.adapter = mAdapter
            this.layoutManager = mLayoutManager
        }
        mAdapter?.onItemClickListener = this
    }

    override fun initEventListener() {
        super.initEventListener()
        mAdapter?.onLoadMoreItems = {
//            context.showToast("Load more")
            loadMore()
//            mAdapter?.isLoading = false
        }
    }

    override fun onItemClick(v: View, position: Int, data: T) {
    }

    override fun onItemLongClick(position: Int, data: T) {
    }

    /**
     * Handle load more items if need
     */
    protected open fun loadMore() {

    }

    protected open fun onResponseDataSuccess(data: List<T>) {
        this.offset += data.size
        mAdapter?.apply {
//            this.isLoading = false
            this.isLoadAllItemDone = data.isEmpty()
        }
    }

    protected open fun onResponseDataFailed(t: Throwable) {
//        mAdapter?.isLoading = false
    }
}