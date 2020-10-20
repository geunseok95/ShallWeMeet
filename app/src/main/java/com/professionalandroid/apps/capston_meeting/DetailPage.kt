package com.professionalandroid.apps.capston_meeting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import com.professionalandroid.apps.capston_meeting.retrofit.board
import kotlinx.android.synthetic.main.fragment_detail_page.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPage : Fragment() {

    lateinit var retrofitService: ConnectRetrofit

    override fun onAttach(context: Context) {
        super.onAttach(context)
        retrofitService  =
            ConnectRetrofit(
                context
            )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_page, container, false)

        // 전 fragment에서 데이터가 넘어왔는지 확인
        if(arguments != null){
            val href = arguments?.getString("href")
            val new_href = StringBuffer(href!!).substring(42).toString()
            val connect_server = retrofitService.retrofitService()
            connect_server.requestSearchSpecificBoard(new_href).enqueue(object: Callback<board> {
                override fun onFailure(call: Call<board>, t: Throwable) {
                    Log.d("test","서버연결 실패 BoardActivity")
                }

                override fun onResponse(call: Call<board>, response: Response<board>) {
                    val board: board = response.body()!!
                    view.detail_title.text = board.title
                    view.detail_tag1.text = board.tag1
                    view.detail_tag2.text = board.tag2
                    view.detail_tag3.text = board.tag3
                    view.detail_location.text = board.location
                    view.detail_num_type.text = board.num_type
                    view.detail_num_type.text = board.gender

                    val image_path1 = board.img1
                    val image_path2 = board.img2
                    val image_path3 = board.img3

                    getimagefromserver(view.detail_img1, image_path1)
                    getimagefromserver(view.detail_img2, image_path2)
                    getimagefromserver(view.detail_img3, image_path3)
                }
            })
        }
        return view
    }

    // 서버에서 이미지 받아오기
    fun getimagefromserver(imageveiw:RoundedImageView, image_path: String) {
        Glide.with(this@DetailPage)
            .load(image_path)
            .centerCrop()
            .into(imageveiw);
    }

}