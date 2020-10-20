package com.professionalandroid.apps.capston_meeting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.professionalandroid.apps.capston_meeting.kakaoLoginService.LoginPage
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import com.professionalandroid.apps.capston_meeting.retrofit.boards
import kotlinx.android.synthetic.main.fragment_my_info.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyInfo : Fragment() {

    lateinit var retrofitService:ConnectRetrofit


    override fun onAttach(context: Context) {
        super.onAttach(context)
        retrofitService = ConnectRetrofit(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_info, container, false)

//        val connect_server = retrofitService.retrofitService()
//        // retrofit 서버연결
//        connect_server.requestSearchBoard().enqueue(object: Callback<boards> {
//            override fun onFailure(call: Call<boards>, t: Throwable) {
//                Log.d("test","서버연결 실패 BoardActivity")
//            }
//
//            override fun onResponse(call: Call<boards>, response: Response<boards>) {
//                val boards: boards = response.body()!!
//
//                val templist = boards._embedded.board_list
//
//            }
//        })




        view.logout.setOnClickListener {
            (activity as MainActivity).logoutService()
        }

        return view
    }



}