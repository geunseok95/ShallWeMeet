package com.professionalandroid.apps.capston_meeting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.fragment_detail_page.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item.view.imageview_description
import kotlinx.android.synthetic.main.list_item.view.imageview_image
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
    ): RecyclerAdapter.ViewHolder {
        val inflateView = LayoutInflater.from(parent.context).inflate(R.layout.list_item2, parent, false)
        return ViewHolder(inflateView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {

        holder.title?.text = items[position].title

        //holder.img1?.setImageResource(Integer.parseInt(items[position].img1))
        holder.location?.text = items[position].location
        holder.num_type?.text = items[position].num_type
        holder.gender?.text = items[position].gender
        holder.index = position
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var parentview = view
        var title: TextView? = null
        var user: TextView? = null
        var img1: RoundedImageView? = null
        var location: TextView? = null
        var num_type: TextView? = null
        var gender: TextView? = null
        var index: Int? = null

        init {
            title = view.imageview_title
            user = view.imageview_user
            img1 = view.imageview_img1
            location = view.imageview_location
            num_type = view.imageview_num_type
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