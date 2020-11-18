package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.ReceiveResponse
import kotlinx.android.synthetic.main.layout_receive_item.view.*

class ReceivePageRecyclerViewAdapter(val receiveList: MutableList<ReceiveResponse>, val context: Context, val listener: OnReceiveClicked): RecyclerView.Adapter<ReceivePageRecyclerViewAdapter.ViewHolder>(), ReceivePageSubRecyclerViewAdapter.OnReceiverClicked {

    lateinit var mreceiver_recyclerview: RecyclerView

    interface OnReceiveClicked{
        fun receiveClicked(view: View, position: Int, senderId: Long, status: Boolean)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var receiver_recyclerview: RecyclerView? = null
        var receive_title: TextView? = null
        var receive_locatoin1: TextView? = null
        var receive_location2: TextView? = null
        var index = 0
        init {
            receiver_recyclerview = view.receiver_recyclerview
            receive_title = view.receive_title
            receive_locatoin1 = view.receive_location1
            receive_location2 = view.receive_location2
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

        val madapter = ReceivePageSubRecyclerViewAdapter(receiveList[position].senders.toMutableList(), context, this, position)
        holder.receiver_recyclerview?.layoutManager = LinearLayoutManager(context)
        holder.receiver_recyclerview?.adapter = madapter
        holder.receiver_recyclerview?.setHasFixedSize(true)
        holder.index = position
        mreceiver_recyclerview = holder.receiver_recyclerview!!
    }

    override fun success(view: View, position: Int, index: Int) {
        val subViewHolder = mreceiver_recyclerview.findViewHolderForAdapterPosition(position) as ReceivePageSubRecyclerViewAdapter.SubViewHolder
        Log.d("test", "success 실행")
        listener.receiveClicked(view, index, subViewHolder.sender_id, subViewHolder.sender_status)
    }
}