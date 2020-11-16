package com.professionalandroid.apps.capston_meeting.src.homePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.viewpager.widget.ViewPager
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.viewpager_list_item
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.android.synthetic.main.fragment_slide_view_page.view.*

class SlideViewPager(viewPagerlist: MutableList<viewpager_list_item>): Fragment() {

    private lateinit var mViewPager: ViewPager
    lateinit var mdots_indicator: DotsIndicator
    private var adapter =
        SlideViewPagerAdapter(
            viewPagerlist
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_slide_view_page, container, false)
        mViewPager = view.fragment_viewpager
        mViewPager.adapter = adapter
        mdots_indicator = view.dots_indicator
        mdots_indicator.setViewPager(mViewPager)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
        mViewPager.adapter = adapter
    }


}