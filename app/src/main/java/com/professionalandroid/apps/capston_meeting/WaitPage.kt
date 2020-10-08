package com.professionalandroid.apps.capston_meeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WaitPage : Fragment() {


    lateinit var mRecyclerView: RecyclerView
    private var mRecyclerAdapter:RecyclerAdapter = RecyclerAdapter(meetinglist!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wait_page, container, false)
        mRecyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context
        mRecyclerView.layoutManager = GridLayoutManager(context,2)
        mRecyclerView.adapter = mRecyclerAdapter
        mRecyclerAdapter.notifyDataSetChanged()
    }

    fun setmeeting(item:MutableList<list_item_data>){
        for(i in item){
            if(!meetinglist!!.contains(i)){
                meetinglist.add(i)
                mRecyclerAdapter.notifyItemInserted(meetinglist.indexOf(i))
            }
        }
    }


}