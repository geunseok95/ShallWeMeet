package com.professionalandroid.apps.capston_meeting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.list_item.view.*

class SlideViewPagerAdapter(private val list: MutableList<viewpager_list_item>):PagerAdapter(){

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.list_item, container, false)

        view.imageview_image.setImageResource(Integer.parseInt(list[position].img1))
        view.imageview_description.text = list[position].title

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