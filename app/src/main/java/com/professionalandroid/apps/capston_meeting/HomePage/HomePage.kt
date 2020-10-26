package com.professionalandroid.apps.capston_meeting.homePage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.professionalandroid.apps.capston_meeting.*
import com.professionalandroid.apps.capston_meeting.applyPage.ApplyPage
import com.professionalandroid.apps.capston_meeting.requestPage.RequestPage
import kotlinx.android.synthetic.main.fragment_home_page.view.*


class HomePage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test", "onCreate")

        val mslideviewpager1: SlideViewPager = SlideViewPager(viewpager_list)
        val mslideviewpager2: SlideViewPager = SlideViewPager(viewpager_list2)
        (activity as MainActivity).replace_fragment(R.id.new_meeting, mslideviewpager1)
        (activity as MainActivity).replace_fragment(R.id.near_meeting, mslideviewpager2)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("test", "onCreateView")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        val meet = view.meet
        val find = view.find
        val applypage = ApplyPage()
        val requestpage = RequestPage()

        meet.setOnClickListener {

            (activity as MainActivity).move_next_fragment(applypage)
        }

        find.setOnClickListener {
            (activity as MainActivity).move_next_fragment(requestpage)
        }

        return view
    }


}