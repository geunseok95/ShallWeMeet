package com.professionalandroid.apps.capston_meeting.src.successPage.successSendPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.successPage.SuccessPageService
import com.professionalandroid.apps.capston_meeting.src.successPage.interfaces.SuccessPageView
import com.professionalandroid.apps.capston_meeting.src.successPage.models.Is
import kotlinx.android.synthetic.main.fragment_success_send_page.view.*

class SuccessSendPage() : Fragment(), SuccessPageView {

    lateinit var mSuccessPageService: SuccessPageService
    lateinit var mSendersRecyclerView: RecyclerView
    lateinit var mSendersRecyclerViewAdapter: SuccessSendersRecyclerViewAdapter
    val sendersList = mutableListOf<Is>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSuccessPageService = SuccessPageService(this, context)
        mSendersRecyclerViewAdapter =
            SuccessSendersRecyclerViewAdapter(
                sendersList,
                context
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendersList.clear()
        mSuccessPageService.getSuccess(MainActivity.user)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_success_send_page, container, false)

        mSendersRecyclerView = view.success_senders_recyclerview
        mSendersRecyclerView.layoutManager = LinearLayoutManager(context)
        mSendersRecyclerView.adapter = mSendersRecyclerViewAdapter

        return view
    }

    override fun getMakers(makersList: List<Is>) {
    }

    override fun getSenders(sendersList: List<Is>) {
        this.sendersList.addAll(sendersList)
        mSendersRecyclerViewAdapter.notifyDataSetChanged()
    }

}