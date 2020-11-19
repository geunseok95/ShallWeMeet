package com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.interfaces.SendPageView
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.models.SendResponse
import com.professionalandroid.apps.capston_meeting.src.detailPage.DetailPage
import kotlinx.android.synthetic.main.fragment_send.view.*

class SendPage : Fragment(), SendPageView, SendPageRecyclerViewAdapter.OnSendItemClicked {

    lateinit var mSendRecyclerView: RecyclerView
    lateinit var mSendRecyclerViewAdapter: SendPageRecyclerViewAdapter
    var sendList = mutableListOf<SendResponse>()

    lateinit var mSendPageService: SendPageService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSendRecyclerViewAdapter = SendPageRecyclerViewAdapter(sendList, context, this)
        mSendPageService = SendPageService(this, context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendList.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_send, container, false)
        mSendRecyclerView = view.send_recyclerview
        mSendRecyclerView.layoutManager = LinearLayoutManager(context)
        mSendRecyclerView.adapter = mSendRecyclerViewAdapter

        mSendPageService.getSend(user)

        return view
    }

    override fun getSend(body: List<SendResponse>) {
        sendList.addAll(body)
        mSendRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun movetoDetail(view: View, position: Int) {
        val viewHolder = mSendRecyclerView.findViewHolderForAdapterPosition(position) as SendPageRecyclerViewAdapter.ViewHolder
        val detailPage = DetailPage().apply{
            arguments = Bundle().apply {
                putLong("href", viewHolder.send_boardId)
            }
        }
        (activity as MainActivity).move_next_fragment(detailPage)
    }

}