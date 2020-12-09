package com.professionalandroid.apps.capston_meeting.src.applyPage

import android.content.Context
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    lateinit var mListener: OnListItemSelelctedInterface

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

    fun setRecyclerView(view:RecyclerView){
        view.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = recyclerView.childCount;
                totalItemCount = gridLayoutManager.itemCount
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
                Log.d("total", totalItemCount.toString());
                Log.d("visible", visibleItemCount.toString());

                Log.d("first", firstVisibleItem.toString());
                Log.d("last", lastVisibleItem.toString());

                if (!isMoreLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    if (mListener != null) {
                        mListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        })
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

        val temp = boards[position]?.date
        val month = temp?.substring(5, 7)
        val date = temp?.substring(8, 10)

        holder.title?.text = boards[position]?.title
        GlideApp.with(mContext)
            .load(boards[position]?.img1)
            .centerCrop()
            .into(holder.img1!!)

        holder.location1?.text = boards[position]?.location1
        holder.location2?.text = boards[position]?.location2
        holder.num_type?.text = boards[position]?.num_type
        holder.date?.text = "${month}월 ${date}일"
        holder.age?.text = boards[position]?.age.toString()
        holder.index = position
        holder.parentview.star_btn.isChecked = boards[position]!!.check
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var parentview = view
        var title: TextView? = null
        var img1: ImageView? = null
        var location1: TextView? = null
        var location2: TextView? = null
        var num_type: TextView? = null
        var date: TextView? = null
        var age: TextView? = null

        // index를 이용해서 상속받은 class에서
        var index: Int? = null

        init {
            img1 = view.imageview_img1
            location1 = view.imageview_location1
            location2 = view.imageview_location2
            age = view.imageview_age
            num_type = view.imageview_num_type
            date = view.imageview_date
            index = 0
            parentview.setOnClickListener {
                mListener.onItemSelected(view, adapterPosition)
            }
            parentview.star_btn.setOnClickListener{
                val position = adapterPosition
                toggleItemSelected(position)
                boards[position]?.check = !boards[position]!!.check
                mListener.onStarChecked(view, adapterPosition)
            }

        }

    }

    fun toggleItemSelected(position:Int){
        if(mSelectedItems[position, false]){
            mSelectedItems.delete(position)
        }
        else{
            mSelectedItems.put(position, true)
        }
        notifyItemChanged(position)
    }


}