package com.professionalandroid.apps.capston_meeting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home_page.view.*


class HomePage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test", "onCreate")

        var mslideviewpager:SlideViewPager? = null
        mslideviewpager = SlideViewPager()
        (activity as MainActivity).replace_fragment(mslideviewpager)

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