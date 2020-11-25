package com.professionalandroid.apps.capston_meeting.src.homePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import com.professionalandroid.apps.capston_meeting.src.homePage.models.RecommendationResponse
import kotlinx.android.synthetic.main.list_item.view.*

class SlideViewPagerAdapter(private val list: MutableList<RecommendationResponse>, val context: Context, val listener: ViewpagerItemSelected): PagerAdapter(){

    interface ViewpagerItemSelected{
        fun ItemClicked(idx: Long)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.list_item, container, false)

        GlideApp.with(context)
            .load(list[position].img1)
            .centerCrop()
            .into(view.hot_image)
        view.hot_location1.text = list[position].location1
        view.hot_location2.text = list[position].location2
        view.hot_age.text = list[position].age.toString()
        view.hot_num_type.text = list[position].num_type

        view.setOnClickListener {
            listener.ItemClicked(list[position].idx)
        }

        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

}