package com.professionalandroid.apps.capston_meeting.src.homePage

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.professionalandroid.apps.capston_meeting.*
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.src.applyPage.ApplyPage
import com.professionalandroid.apps.capston_meeting.src.detailPage.DetailPage
import com.professionalandroid.apps.capston_meeting.src.homePage.interfaces.HomepageView
import com.professionalandroid.apps.capston_meeting.src.homePage.models.RecommendationResponse
import com.professionalandroid.apps.capston_meeting.src.requestPage.RequestPage
import kotlinx.android.synthetic.main.fragment_home_page.view.*


class HomePage : Fragment(), HomepageView, SlideViewPagerAdapter.ViewpagerItemSelected{

    lateinit var mHomepageService: HomepageService

    val hotTimeList = mutableListOf<RecommendationResponse>()
    val hotNearList = mutableListOf<RecommendationResponse>()
    lateinit var mslideviewpager1: SlideViewPager
    lateinit var mslideviewpager2: SlideViewPager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mHomepageService = HomepageService(this, context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        hotTimeList.clear()
        hotNearList.clear()

        mslideviewpager1 = SlideViewPager(hotTimeList, this)
        mslideviewpager2= SlideViewPager(hotNearList, this)

        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        val meet = view.meet
        val find = view.find
        val applypage = ApplyPage()
        val requestpage = RequestPage()

        (activity as MainActivity).replace_fragment(R.id.new_meeting, mslideviewpager1)
        (activity as MainActivity).replace_fragment(R.id.near_meeting, mslideviewpager2)

        meet.setOnClickListener {
            (activity as MainActivity).move_next_fragment(applypage)
        }

        find.setOnClickListener {
            (activity as MainActivity).move_next_fragment(requestpage)
        }
        mHomepageService.hotTime(user)
        mHomepageService.hotNear(user)
        return view
    }

    override fun hotTime(hotTimeList: List<RecommendationResponse>) {
        this.hotTimeList.addAll(hotTimeList)
        mslideviewpager1.adapter.notifyDataSetChanged()

    }

    override fun hotNear(hotNearList: List<RecommendationResponse>) {
        this.hotNearList.addAll(hotNearList)
        mslideviewpager2.adapter.notifyDataSetChanged()
    }

    override fun ItemClicked(idx: Long) {
        val detailPage = DetailPage().apply{
            arguments = Bundle().apply {
                putLong("href", idx)
            }
        }
        (activity as MainActivity).move_next_fragment(detailPage)
    }


}