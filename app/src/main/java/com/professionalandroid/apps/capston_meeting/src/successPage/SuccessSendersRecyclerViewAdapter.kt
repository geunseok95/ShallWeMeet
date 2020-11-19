package com.professionalandroid.apps.capston_meeting.src.successPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import com.professionalandroid.apps.capston_meeting.src.successPage.models.Is
import kotlinx.android.synthetic.main.layout_success_item.view.*

class SuccessSendersRecyclerViewAdapter(val sendersList: MutableList<Is>, val context: Context): RecyclerView.Adapter<SuccessSendersRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var success_img: RoundedImageView? = null
        var success_title: TextView? = null
        var success_nickName: TextView? = null
        var success_location1: TextView? = null
        var success_location2: TextView? = null
        var success_age: TextView? = null
        var success_num_type: TextView? = null
        var success_kakao_id: TextView? = null

        init {
            success_img = view.success_img
            success_title = view.success_title
            success_nickName = view.success_nickName
            success_location1 = view.success_location1
            success_location2 = view.success_location2
            success_age = view.success_age
            success_num_type = view.success_num_type
            success_kakao_id = view.success_kakao_id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateview = LayoutInflater.from(parent.context).inflate(R.layout.layout_success_item, parent, false)
        return ViewHolder(inflateview)
    }

    override fun getItemCount(): Int {
        return sendersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(context)
            .load(sendersList[position].board.img1)
            .centerCrop()
            .into(holder.success_img!!)
        holder.success_title?.text = sendersList[position].board.title
        holder.success_nickName?.text = sendersList[position].board.user.nickName
        holder.success_location1?.text = sendersList[position].board.location1
        holder.success_location2?.text = sendersList[position].board.location2
        holder.success_age?.text = sendersList[position].board.user.age
        holder.success_num_type?.text = sendersList[position].board.num_type
        holder.success_kakao_id?.text = sendersList[position].board.user.kakao_id
    }
}