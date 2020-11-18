package com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.BaseActivity
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.models.SendResponse
import kotlinx.android.synthetic.main.layout_send_item.view.*

class SendPageRecyclerViewAdapter(val sendList: MutableList<SendResponse>, val context: Context, val listener: OnSendItemClicked): RecyclerView.Adapter<SendPageRecyclerViewAdapter.ViewHolder>() {

    interface OnSendItemClicked{
        fun movetoDetail(view:View, position: Int)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val parent = view
        var send_image: RoundedImageView? = null
        var send_title: TextView? = null
        var send_location1: TextView? = null
        var send_location2: TextView? = null
        var send_age: TextView? = null
        var send_num_type: TextView? = null
        var send_boardId: Long = 0

        init {
            send_image = view.send_image
            send_title = view.send_title
            send_location1 = view.send_location1
            send_location2 = view.send_location2
            send_age = view.send_age
            send_num_type = view.send_num_type
            parent.setOnClickListener {
                listener.movetoDetail(view, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateview = LayoutInflater.from(parent.context).inflate(R.layout.layout_send_item, parent, false)
        return ViewHolder(inflateview)
    }

    override fun getItemCount(): Int {
        return sendList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(context)
            .load(sendList[position].board.img1)
            .centerCrop()
            .into(holder.send_image!!)

        holder.send_title?.text = sendList[position].board.title
        holder.send_location1?.text = sendList[position].board.location1
        holder.send_location2?.text = sendList[position].board.location2
        holder.send_age?.text = sendList[position].board.age.toString()
        holder.send_num_type?.text = sendList[position].board.num_type
        holder.send_boardId = sendList[position].board.idx
    }
}