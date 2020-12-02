package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.ReceiveResponse
import kotlinx.android.synthetic.main.layout_receive_item.view.*

class ReceivePageRecyclerViewAdapter(val receiveList: MutableList<ReceiveResponse>, val context: Context, val listener: OnReceiveClicked, val mlistener: ReceivePageSubRecyclerViewAdapter.OnReceiverClicked): RecyclerView.Adapter<ReceivePageRecyclerViewAdapter.ViewHolder>(){

    lateinit var mReceiverRecyclerView: RecyclerView

    interface OnReceiveClicked{
        fun myPageClicked(view: View, position: Int)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var parentView: ConstraintLayout? = null
        var receive_image:RoundedImageView? = null
        var receiver_recyclerview: RecyclerView? = null
        var receive_title: TextView? = null
        var receive_locatoin1: TextView? = null
        var receive_location2: TextView? = null
        var receive_num_type: TextView? = null
        var boardId: Long = 0
        var index = 0
        init {
            receiver_recyclerview = view.receiver_recyclerview
            receive_title = view.receive_title
            receive_locatoin1 = view.receive_location1
            receive_location2 = view.receive_location2
            receive_num_type = view.receive_num_type
            receive_image = view.receive_image
            parentView = view.receive_page.apply {
                setOnClickListener {
                    listener.myPageClicked(view, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateview = LayoutInflater.from(parent.context).inflate(R.layout.layout_receive_item, parent, false)
        return ViewHolder(inflateview)
    }

    override fun getItemCount(): Int {
        return receiveList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.receive_title?.text = receiveList[position].title
        holder.receive_locatoin1?.text = receiveList[position].location1
        holder.receive_location2?.text = receiveList[position].location2
        holder.receive_num_type?.text = receiveList[position].num_type
        holder.boardId = receiveList[position].idx
        holder.index = position

        val madapter = ReceivePageSubRecyclerViewAdapter(receiveList[position].senders.toMutableList(), context, mlistener, holder.boardId)
        holder.receiver_recyclerview?.layoutManager = LinearLayoutManager(context)
        holder.receiver_recyclerview?.adapter = madapter
        holder.receiver_recyclerview?.setHasFixedSize(true)
        holder.boardId = receiveList[position].idx
        GlideApp.with(context)
            .load(receiveList[position].img1)
            .centerCrop()
            .into(holder.receive_image!!)

        mReceiverRecyclerView = holder.receiver_recyclerview!!
    }

}