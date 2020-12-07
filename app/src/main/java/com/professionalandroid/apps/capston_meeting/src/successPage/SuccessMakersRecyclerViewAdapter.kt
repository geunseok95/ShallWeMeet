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

class SuccessMakersRecyclerViewAdapter(val makersList: MutableList<Is>, val context: Context): RecyclerView.Adapter<SuccessMakersRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var success_img: RoundedImageView? = null
        var success_title: TextView? = null
        var success_nickName: TextView? = null
        var success_location1: TextView? = null
        var success_location2: TextView? = null
        var success_age: TextView? = null
        var success_num_type: TextView? = null
        var success_phone_number: TextView? = null

        init {
            success_img = view.success_img
            success_title = view.success_title
            success_nickName = view.success_nickName
            success_location1 = view.success_location1
            success_location2 = view.success_location2
            success_age = view.success_age
            success_num_type = view.success_num_type
            success_phone_number = view.success_phone_number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateview = LayoutInflater.from(parent.context).inflate(R.layout.layout_success_item, parent, false)
        return ViewHolder(inflateview)
    }

    override fun getItemCount(): Int {
        return makersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(context)
            .load(makersList[position].sender.img)
            .centerCrop()
            .into(holder.success_img!!)
        holder.success_title?.text = makersList[position].board.title
        holder.success_nickName?.text = makersList[position].sender.nickName
        holder.success_location1?.text = makersList[position].board.location1
        holder.success_location2?.text = makersList[position].board.location2
        holder.success_age?.text = makersList[position].sender.age
        holder.success_num_type?.text = makersList[position].board.num_type
        holder.success_phone_number?.text = makersList[position].sender.phone
    }
}