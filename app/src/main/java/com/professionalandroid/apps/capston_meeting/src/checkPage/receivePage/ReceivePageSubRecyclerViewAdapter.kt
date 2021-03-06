package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.Sender
import kotlinx.android.synthetic.main.layout_receiver_item.view.*

class ReceivePageSubRecyclerViewAdapter(val receiverList: MutableList<Sender>, val context: Context, val listener: OnReceiverClicked, val parent_position: Int):RecyclerView.Adapter<ReceivePageSubRecyclerViewAdapter.SubViewHolder>() {

    interface OnReceiverClicked{
        fun successMatch(parent_position: Int, position: Int)
    }

    inner class SubViewHolder(view: View): RecyclerView.ViewHolder(view){
        var parentview = view
        var receiver_image: RoundedImageView? = null
        var receiver_nickName: TextView? = null
        var receiver_age: TextView? = null
        var receiver_status: TextView? = null
        init {
            receiver_image = view.receiver_image
            receiver_nickName = view.receiver_nickNave
            receiver_age = view.receiver_age
            receiver_status = view.receiver_status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubViewHolder {
        val inflateview = LayoutInflater.from(parent.context).inflate(R.layout.layout_receiver_item, parent, false)
        return SubViewHolder(inflateview)
    }

    override fun getItemCount(): Int {
        return receiverList.size
    }

    override fun onBindViewHolder(holder: SubViewHolder, position: Int) {
        GlideApp.with(context)
            .load(receiverList[position].img)
            .centerCrop()
            .into(holder.receiver_image!!)
        holder.receiver_nickName?.text = receiverList[position].nickName
        holder.receiver_age?.text = receiverList[position].age
        holder.parentview.setOnClickListener {
            listener.successMatch(parent_position, position)
        }
        if(receiverList[position].status){
            holder.receiver_status?.text = "결제가 완료되었어요"
        }
        else{
            holder.receiver_status?.text = "결제가 필요해요"
        }
    }
}