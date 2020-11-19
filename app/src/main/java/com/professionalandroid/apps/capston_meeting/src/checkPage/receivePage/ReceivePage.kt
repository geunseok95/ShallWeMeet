package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.interfaces.ReceivePageView
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.Permit
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.ReceiveResponse
import kotlinx.android.synthetic.main.fragment_receive.view.*

class ReceivePage : Fragment(), ReceivePageView,ReceivePageRecyclerViewAdapter.OnReceiveClicked, ReceivePopUpWindow.MyDialogOKClickedListener {

    lateinit var mReceivePageRecyclerView: RecyclerView
    lateinit var mReceivePageRecyclerViewAdapter: ReceivePageRecyclerViewAdapter
    lateinit var mReceivePageService: ReceivePageService
    val receiveList = mutableListOf<ReceiveResponse>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mReceivePageRecyclerViewAdapter = ReceivePageRecyclerViewAdapter(receiveList, context, this)
        mReceivePageService = ReceivePageService(this, context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiveList.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_receive, container, false)
        mReceivePageRecyclerView = view.receive_recyclerview
        mReceivePageRecyclerView.layoutManager = LinearLayoutManager(context)
        mReceivePageRecyclerView.adapter = mReceivePageRecyclerViewAdapter

        mReceivePageService.getReceive(user)

        return view
    }

    override fun receiveClicked(view: View, position: Int, senderId: Long, status: Boolean) {
        if(status){
            val popUp = ReceivePopUpWindow(context!!, this)
            popUp.start("신청을 수락할까요?", 1, position, senderId, status)
        }
        else{
            val popUp = ReceivePopUpWindow(context!!, this)
            popUp.start("결제를 진행할까요?", 0, position, senderId, status)
        }
    }

    override fun onOKClicked(success: Int, position: Int, senderId: Long, status: Boolean) {
        if(success == 1){
            val permit = Permit(receiveList[position].idx, senderId)
            mReceivePageService.trySuccess(permit, position)
        }
        else{
            val permit = Permit(receiveList[position].idx, senderId)
            mReceivePageService.tryPaySuccess(permit, position)
        }
    }

    override fun getReceive(body: List<ReceiveResponse>) {
        receiveList.addAll(body)
        mReceivePageRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun success(positon: Int) {
        receiveList.removeAt(positon)
        mReceivePageRecyclerViewAdapter.notifyDataSetChanged()
    }

}