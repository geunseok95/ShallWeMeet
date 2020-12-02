package com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.BaseActivity
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.models.Matched
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.models.SendResponse
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.android.synthetic.main.layout_send_item.view.*

class SendPageRecyclerViewAdapter(val sendList: MutableList<SendResponse>, val context: Context, val listener: SendPageViewPagerAdapter.SendViewPagerItemSelected): RecyclerView.Adapter<SendPageRecyclerViewAdapter.ViewHolder>() {

    lateinit var mSubViewPager: ViewPager2

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val parent = view
        var send_month: TextView? = null
        var send_date: TextView? = null
        var send_viewPager: ViewPager2? = null
        var send_dotIndicator: DotsIndicator? = null
        init {
            //send_month = view.send_month
            send_date = view.send_date
            send_viewPager = view.send_viewpager
            send_dotIndicator = view.send_dots_indicator
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
        val temp = sendList[position].date
        var month = temp.substring(5, 7)
        var date = temp.substring(8, 10)

        if(month[0] == '0'){
            month = month[1].toString()
        }

        if(date[0] == '0'){
            date = date[1].toString()
        }

        holder.send_month?.text = "  $month 월  "
        holder.send_date?.text = "$date 일"

        val madapter = SendPageViewPagerAdapter(sendList[position].matched.toMutableList(), context, listener)
        holder.send_viewPager?.adapter = madapter
        holder.send_dotIndicator?.setViewPager2(holder.send_viewPager!!)
        mSubViewPager = holder.send_viewPager!!
    }
}