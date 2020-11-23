package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.interfaces.MyDetailPageView
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.models.MyDetailResponse
import kotlinx.android.synthetic.main.fragment_my_detail_page.*
import kotlinx.android.synthetic.main.fragment_my_detail_page.view.*

class MyDetailPage : Fragment(), MyDetailPageView {

    lateinit var mMyDetailPageService: MyDetailPageService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mMyDetailPageService = MyDetailPageService(this, context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_detail_page, container, false)
        val boardId = arguments?.getLong("boardId", 0)

        mMyDetailPageService.getMyDetail(boardId!!, user)

        view.my_detail_delete_btn.setOnClickListener {
            mMyDetailPageService.deletePage(boardId)
        }

        return view
    }

    override fun getMyDetail(body: MyDetailResponse) {
        my_detail_title.text = body.title
        my_detail_tag1.text = body.tag1
        my_detail_tag2.text = body.tag2
        my_detail_tag3.text = body.tag3
        my_detail_location.text = body.location1
        my_detail_num_type.text = body.num_type

        (activity as MainActivity).displayImg(context!!, body.img1 ,my_detail_img1)
        (activity as MainActivity).displayImg(context!!, body.img2 ,my_detail_img2)
        (activity as MainActivity).displayImg(context!!, body.img3 ,my_detail_img3)
    }

    override fun deletePage() {
        (activity as MainActivity).close_fragment(this)
    }

}