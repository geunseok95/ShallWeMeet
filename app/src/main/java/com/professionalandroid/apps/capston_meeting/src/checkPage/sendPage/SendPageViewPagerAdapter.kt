package com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.models.Matched
import kotlinx.android.synthetic.main.layout_send_viewpager_item.view.*

class SendPageViewPagerAdapter(val sendViewPagerList: MutableList<Matched>, val context: Context, val listener: SendViewPagerItemSelected): RecyclerView.Adapter<SendPageViewPagerAdapter.SubViewHolder>(){

    interface SendViewPagerItemSelected{
        fun itemClicked(boardId: Long)
    }

    inner class SubViewHolder(view: View): RecyclerView.ViewHolder(view){
        val parent = view
        var sender_image: RoundedImageView? = null
        var sender_title: TextView? = null
        var sender_location1: TextView? = null
        var sender_location2: TextView? = null
        var sender_num_type: TextView? = null
        var sender_boardId: Long = 0

        init {
            sender_image = view.sender_image
            sender_title = view.sender_title
            sender_location1 = view.sender_location1
            sender_location2 = view.sender_location2
            sender_num_type = view.sender_num_type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubViewHolder {
        val inflateview = LayoutInflater.from(parent.context).inflate(R.layout.layout_send_viewpager_item, parent, false)
        return SubViewHolder(inflateview)
    }

    override fun getItemCount(): Int {
        return sendViewPagerList.size
    }

    override fun onBindViewHolder(holder: SubViewHolder, position: Int) {
        holder.sender_title?.text = sendViewPagerList[position].board.title
        holder.sender_location1?.text = sendViewPagerList[position].board.location1
        holder.sender_location2?.text = sendViewPagerList[position].board.location2
        holder.sender_num_type?.text = sendViewPagerList[position].board.num_type
        holder.sender_boardId = sendViewPagerList[position].board.idx
        holder.parent.setOnClickListener {
            listener.itemClicked(holder.sender_boardId)
        }
        GlideApp.with(context)
            .load(sendViewPagerList[position].board.img1)
            .centerCrop()
            .into(holder.sender_image!!)
    }
}