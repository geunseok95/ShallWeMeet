package com.professionalandroid.apps.capston_meeting.src.myInfoPage

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.professionalandroid.apps.capston_meeting.MainActivity
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import com.professionalandroid.apps.capston_meeting.retrofit.user_info
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.interfaces.MyInfoPageView
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.models.MyInfoResponse
import kotlinx.android.synthetic.main.fragment_my_info_page.*
import kotlinx.android.synthetic.main.fragment_my_info_page.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        view.logout.setOnClickListener {
            (activity as MainActivity).logoutService()
        }
        return view
    }

    override fun getInfo(body: MyInfoResponse) {

        my_info_nickname.text = body.nickName
        (activity as MainActivity).displayImg(context!!, body.img, my_info_image)
        my_info_location.text = body.location
    }
}