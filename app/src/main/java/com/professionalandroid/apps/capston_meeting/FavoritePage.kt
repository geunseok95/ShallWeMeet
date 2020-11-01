package com.professionalandroid.apps.capston_meeting

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.applyPage.RecyclerAdapter
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import com.professionalandroid.apps.capston_meeting.retrofit.RetrofitService
import com.professionalandroid.apps.capston_meeting.retrofit.board
import com.professionalandroid.apps.capston_meeting.retrofit.favorite
import kotlinx.android.synthetic.main.fragment_favorite_page.view.*
import kotlinx.android.synthetic.main.list_item2.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritePage : Fragment(),RecyclerAdapter.OnListItemSelelctedInterface {

    var boards = mutableListOf<board?>()

    lateinit var connect_server: RetrofitService
    lateinit var mRecyclerView: RecyclerView
    private var mRecyclerAdapter: RecyclerAdapter? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mRecyclerAdapter = RecyclerAdapter(context, this, boards)
        connect_server = ConnectRetrofit(context).retrofitService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_page, container, false)
        mRecyclerView = view.favorite_recyclerview
        mRecyclerView.layoutManager = GridLayoutManager(context,2)
        mRecyclerView.adapter = mRecyclerAdapter

        connect_server.getFavorite(user).enqueue(object : Callback<List<board>>{
            override fun onFailure(call: Call<List<board>>, t: Throwable) {
            }

            override fun onResponse(call: Call<List<board>>, response: Response<List<board>>) {
                boards.addAll(response.body()!!)
                for(i in boards){
                    i?.check = true
                }
                mRecyclerAdapter?.notifyDataSetChanged()
            }
        })
        return view
    }

    override fun onItemSelected(v: View, position: Int) {
        val viewholder: RecyclerAdapter.ViewHolder = mRecyclerView.findViewHolderForAdapterPosition(position) as RecyclerAdapter.ViewHolder

        val detailpage = DetailPage()
        val bundle = Bundle()

        Log.d("test_position", position.toString())
        Log.d("test", "${viewholder.index}")
        bundle.putLong("href", boards[viewholder.index!!]!!.idx)
        detailpage.arguments = bundle
        (activity as MainActivity).move_next_fragment(detailpage)    }

    override fun onStarChecked(v: View, position: Int) {
        if(!v.star_btn.isChecked){
            val viewholder: RecyclerAdapter.ViewHolder = mRecyclerView.findViewHolderForAdapterPosition(position) as RecyclerAdapter.ViewHolder
            connect_server.deleteFavorite(user, boards[viewholder.index!!]?.idx!!).enqueue(object : Callback<favorite>{
                override fun onFailure(call: Call<favorite>, t: Throwable) {
                    Log.d("test", "실패")
                }

                override fun onResponse(call: Call<favorite>, response: Response<favorite>) {
                    boards.removeAt(viewholder.index!!)
                    mRecyclerAdapter?.notifyDataSetChanged()
                    Log.d("test", "성공")
                }

            })
        }
    }

}