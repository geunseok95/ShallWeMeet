package com.professionalandroid.apps.capston_meeting.src.successPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.src.successPage.interfaces.SuccessPageView
import com.professionalandroid.apps.capston_meeting.src.successPage.models.Is
import kotlinx.android.synthetic.main.fragment_success_page.view.*

class SuccessPage : Fragment(), SuccessPageView {

    lateinit var mSuccessPageService: SuccessPageService
    lateinit var mMakersRecyclerView: RecyclerView
    lateinit var mMakersRecyclerViewAdapter: SuccessMakersRecyclerViewAdapter
    lateinit var mSendersRecyclerView: RecyclerView
    lateinit var mSendersRecyclerViewAdapter: SuccessSendersRecyclerViewAdapter
    val makersList = mutableListOf<Is>()
    val sendersList = mutableListOf<Is>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSuccessPageService = SuccessPageService(this, context)
        mMakersRecyclerViewAdapter = SuccessMakersRecyclerViewAdapter(makersList, context)
        mSendersRecyclerViewAdapter = SuccessSendersRecyclerViewAdapter(sendersList, context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makersList.clear()
        sendersList.clear()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_success_page, container, false)
        mMakersRecyclerView = view.success_makers_recyclerview
        mMakersRecyclerView.layoutManager = LinearLayoutManager(context)
        mMakersRecyclerView.adapter = mMakersRecyclerViewAdapter

        mSendersRecyclerView = view.success_senders_recyclerview
        mSendersRecyclerView.layoutManager = LinearLayoutManager(context)
        mSendersRecyclerView.adapter = mSendersRecyclerViewAdapter

        mSuccessPageService.getSuccess(user)

        return view
    }

    override fun getMakers(makersList: List<Is>) {
        this.makersList.addAll(makersList)
        mMakersRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun getSenders(sendersList: List<Is>) {
        this.sendersList.addAll(sendersList)
        mSendersRecyclerViewAdapter.notifyDataSetChanged()
    }


}