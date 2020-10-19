package com.professionalandroid.apps.capston_meeting.applyPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.list_item_data
import kotlinx.android.synthetic.main.list_item2.view.*

class RecyclerAdapter(private var items: MutableList<list_item_data>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    // list와 연결할 listener
    interface OnListItemSelelctedInterface{
        fun onItemSelected(v: View, position: Int)
    }

    var mContext: Context? = null
    private var mListener: OnListItemSelelctedInterface? = null
    var meetinglist: MutableList<list_item_data>? = null

    constructor(context: Context, listener: OnListItemSelelctedInterface, meetinglist: MutableList<list_item_data>) : this(meetinglist) {
        this.mContext = context
        this.mListener = listener
        this.meetinglist = meetinglist
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflateView = LayoutInflater.from(parent.context).inflate(R.layout.list_item2, parent, false)
        return ViewHolder(inflateView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title?.text = items[position].title
        Glide.with(mContext!!)
            .load(items[position].img1)
            .centerCrop()
            .into(holder.img1!!);
        holder.location?.text = items[position].location
        holder.num_type?.text = items[position].num_type
        holder.age?.text = items[position].age
        holder.tag1?.text = items[position].tag1
        holder.tag2?.text = items[position].tag2
        holder.tag3?.text = items[position].tag3
        holder.index = position
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

        }

        override fun toString(): String {
            return super.toString() + " '" + title!!.text + "'"
        }
    }
}