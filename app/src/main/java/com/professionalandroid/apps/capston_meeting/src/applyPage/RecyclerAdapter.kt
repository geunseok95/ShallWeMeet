package com.professionalandroid.apps.capston_meeting.src.applyPage

import android.content.Context
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.applyPage.models.ApplyResponse
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import kotlinx.android.synthetic.main.list_item2.view.*

class RecyclerAdapter():
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    var isMoreLoading = false // 다음 페이지 유무
    var visibleThreshold = 1
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    var lastVisibleItem = 0
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var boards :MutableList<ApplyResponse?>
    lateinit var mContext: Context
    private var mListener: OnListItemSelelctedInterface? = null

    // list와 연결할 listener
    interface OnListItemSelelctedInterface{
        fun onItemSelected(v: View, position: Int)
        fun onStarChecked(v: View, position: Int)
        fun onLoadMore()
    }

    val mSelectedItems: SparseBooleanArray = SparseBooleanArray(0)

    constructor(context: Context, listener: OnListItemSelelctedInterface, boards: MutableList<ApplyResponse?>, gridLayoutManager: GridLayoutManager) : this() {
        this.mContext = context
        this.mListener = listener
        this.boards = boards
        this.gridLayoutManager = gridLayoutManager
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflateView = LayoutInflater.from(parent.context).inflate(R.layout.list_item2, parent, false)
        return ViewHolder(inflateView)
    }

    override fun getItemCount(): Int {
        return boards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title?.text = boards[position]?.title
        GlideApp.with(mContext!!)
            .load(boards[position]?.img1)
            .centerCrop()
            .into(holder.img1!!)

        holder.location?.text = boards[position]?.location1
        holder.num_type?.text = boards[position]?.num_type
        holder.age?.text = boards[position]?.age.toString()
        holder.tag1?.text = boards[position]?.tag1
        holder.tag2?.text = boards[position]?.tag2
        holder.tag3?.text = boards[position]?.tag3
        holder.index = position
        holder.parentview.star_btn.isChecked = boards[position]!!.check
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var parentview = view
        var title: TextView? = null
        var img1: RoundedImageView? = null
        var location: TextView? = null
        var num_type: TextView? = null
        var age: TextView? = null
        var tag1: TextView? = null
        var tag2: TextView? = null
        var tag3: TextView? = null

        // index를 이용해서 상속받은 class에서
        var index: Int? = null

        init {
            title = view.imageview_title
            img1 = view.imageview_img1
            location = view.imageview_location
            age = view.imageview_age
            num_type = view.imageview_num_type
            tag1 = view.imageview_tag1
            tag2 = view.imageview_tag2
            tag3 = view.imageview_tag3
            index = 0
            parentview.item2_card_view.setOnClickListener {
                mListener?.onItemSelected(view, adapterPosition)
            }
            parentview.star_btn.setOnClickListener{
                val position = adapterPosition
                toggleItemSelectied(position)
                boards[position]?.check = !boards[position]!!.check
                mListener?.onStarChecked(view, adapterPosition)
            }

        }

        override fun toString(): String {
            return super.toString() + " '" + title!!.text + "'"
        }
    }

    fun toggleItemSelectied(position:Int){
        if(mSelectedItems[position, false]){
            mSelectedItems.delete(position)
        }
        else{
            mSelectedItems.put(position, true)
        }
        notifyItemChanged(position)
    }

    fun setRecyclerView(view:RecyclerView){
        view.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = gridLayoutManager.itemCount
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
                Log.d("total", totalItemCount.toString());
                Log.d("visible", visibleItemCount.toString());

                Log.d("first", firstVisibleItem.toString());
                Log.d("last", lastVisibleItem.toString());

                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
                    if (mListener != null) {
                        mListener?.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        })
    }
}