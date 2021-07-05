package pathmazing.com.recycleradapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chipmong.dms.adapter.BaseRecyclerAdapter
import com.google.gson.Gson

/**
 *
 *
 * @author
 * @version
 * @created on 22-Jun-18
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected val mGson = Gson()

    open fun isDefaultClick(): Boolean = true


    abstract fun binData(baseRecyclerAdapter: BaseRecyclerAdapter<T>)

    open fun onViewRecycler() {
        itemView.setOnClickListener(null)
    }

}