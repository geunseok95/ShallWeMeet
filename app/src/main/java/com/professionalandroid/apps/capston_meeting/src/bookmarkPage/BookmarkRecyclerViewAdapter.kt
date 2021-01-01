package com.professionalandroid.apps.capston_meeting.src.bookmarkPage

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import com.professionalandroid.apps.capston_meeting.src.bookmarkPage.models.BookmarkResponse
import kotlinx.android.synthetic.main.list_item2.view.*

class BookmarkRecyclerViewAdapter(val bookmarkList: MutableList<BookmarkResponse>): RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder>() {

    // list와 연결할 listener
    interface OnListItemSelelctedInterface{
        fun onItemSelected(v: View, position: Int)
        fun onStarChecked(v: View, position: Int)
    }

    var mContext: Context? = null
    private var mListener: BookmarkRecyclerViewAdapter.OnListItemSelelctedInterface? = null

    constructor(context: Context, listener: BookmarkRecyclerViewAdapter.OnListItemSelelctedInterface, bookmarkList: MutableList<BookmarkResponse>) : this(bookmarkList) {
        this.mContext = context
        this.mListener = listener
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
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
                mListener?.onItemSelected(view, adapterPosition)
            }
            parentview.star_btn.apply {
                this.isChecked = true
                setOnClickListener {
                    mListener?.onStarChecked(view, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateview = LayoutInflater.from(parent.context).inflate(R.layout.list_item2, parent, false)
        return ViewHolder(inflateview)
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val temp = bookmarkList[position].board.date
        val month = temp.substring(5, 7)
        val date = temp.substring(8, 10)

        holder.title?.text = bookmarkList[position].board.title
        GlideApp.with(mContext!!)
            .load(bookmarkList[position].board.img1)
            .centerCrop()
            .into(holder.img1!!)

        holder.location1?.text = bookmarkList[position].board.location1
        holder.location2?.text = bookmarkList[position].board.location2
        holder.num_type?.text = bookmarkList[position].board.num_type
        holder.date?.text = "${month}월 ${date}일"
        holder.age?.text = bookmarkList[position].board.age.toString()
        holder.index = position
    }


}