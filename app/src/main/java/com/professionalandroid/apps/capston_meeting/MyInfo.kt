package com.professionalandroid.apps.capston_meeting

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import com.professionalandroid.apps.capston_meeting.retrofit.user_info
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

        val connect_server = retrofitService.retrofitService()
        // retrofit 서버연결
        connect_server.getuserinfo(user).enqueue(object: Callback<user_info> {
            override fun onFailure(call: Call<user_info>, t: Throwable) {
                Log.d("test","서버연결 실패 BoardActivity")
            }

            override fun onResponse(call: Call<user_info>, response: Response<user_info>) {
                val info: user_info = response.body()!!
                view.my_info_nickname.text = info.nickName
                Glide.with(context!!)
                    .load(info.img)
                    .centerCrop()
                    .into(view.my_info_image)
                view.my_info_location.text = info.location

            }
        })

        view.logout.setOnClickListener {
            (activity as MainActivity).logoutService()
        }

        return view
    }



}