package com.professionalandroid.apps.capston_meeting.src.myInfoPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.interfaces.MyInfoPageView
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.models.MyInfoResponse
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.modifyMyInfoPage.ModifyMyInfoPage
import kotlinx.android.synthetic.main.fragment_my_info_page.*
import kotlinx.android.synthetic.main.fragment_my_info_page.view.*

class MyInfoPage : Fragment(), MyInfoPageView {

    lateinit var mMyInfoPageService: MyInfoPageService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mMyInfoPageService = MyInfoPageService(this, context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_info_page, container, false)

        mMyInfoPageService.getInfo(user)

        view.my_info_modify.setOnClickListener {
            val modifyMyInfoPage = ModifyMyInfoPage().apply {
                arguments = Bundle().apply {
                    putString("image", view.my_info_image.contentDescription.toString())
                    putString("nickName", view.my_info_nickname.text.toString())
                }
            }
            (activity as MainActivity).move_next_fragment(modifyMyInfoPage)
        }

        view.logout.setOnClickListener {
            (activity as MainActivity).logoutService()
        }
        return view
    }

    override fun getInfo(body: MyInfoResponse) {
        my_info_nickname.text = body.nickName
        (activity as MainActivity).displayImg(context!!, body.img, my_info_image)
        my_info_location1.text = body.location1
        my_info_location2.text = body.location2
        my_info_age.text = body.age
        my_info_phone.text = body.phone
        my_info_point.text = body.point.toString()
    }
}