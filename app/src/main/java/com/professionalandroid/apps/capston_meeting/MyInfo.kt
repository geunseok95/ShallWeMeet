package com.professionalandroid.apps.capston_meeting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.professionalandroid.apps.capston_meeting.kakaoLoginService.LoginPage
import kotlinx.android.synthetic.main.fragment_my_info.view.*

class MyInfo : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_info, container, false)
        view.logout.setOnClickListener {
            (activity as MainActivity).logoutService()
        }

        return view
    }

}