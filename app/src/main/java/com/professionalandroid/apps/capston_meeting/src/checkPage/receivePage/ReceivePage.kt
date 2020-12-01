package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.interfaces.ReceivePageView
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.Permit
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.ReceiveResponse
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.MyDetailPage
import kotlinx.android.synthetic.main.fragment_receive.view.*
import kotlin.math.abs

class ReceivePage : Fragment(), ReceivePageView,ReceivePageRecyclerViewAdapter.OnReceiveClicked, ReceivePopUpWindow.MyDialogOKClickedListener,  ReceivePageSubRecyclerViewAdapter.OnReceiverClicked  {

    lateinit var mReceivePageRecyclerView: ViewPager2
    lateinit var mReceivePageRecyclerViewAdapter: ReceivePageRecyclerViewAdapter
    lateinit var mReceivePageService: ReceivePageService
    val receiveList = mutableListOf<ReceiveResponse>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mReceivePageRecyclerViewAdapter = ReceivePageRecyclerViewAdapter(receiveList, context, this, this)
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
        mReceivePageRecyclerView.adapter = mReceivePageRecyclerViewAdapter


        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
        view.receive_recyclerview.setPageTransformer { page, position -> val myOffset = position * -(2 * pageOffset + pageMargin)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) { val scaleFactor =
                0.7f.coerceAtLeast(1 - abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            }
            else {
                page.alpha = 0f
                page.translationX = myOffset
            } }


        mReceivePageService.getReceive(user)

        return view
    }


    override fun myPageClicked(view:View, position: Int) {
        val viewHolder = (mReceivePageRecyclerView.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(position) as ReceivePageRecyclerViewAdapter.ViewHolder
        val boardId = viewHolder.boardId
        val myDetailPage = MyDetailPage().apply {
            arguments = Bundle().apply {
                putLong("boardId", boardId)
            }
        }
        (activity as MainActivity).move_next_fragment(myDetailPage)
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

    override fun success(view: View, position: Int, index: Int) {
        val subViewHolder = mReceivePageRecyclerViewAdapter.mReceiverRecyclerView.findViewHolderForAdapterPosition(index) as ReceivePageSubRecyclerViewAdapter.SubViewHolder
        if(subViewHolder.sender_status){
            val popUp = ReceivePopUpWindow(context!!, this)
            popUp.start("신청을 수락할까요?", 1, position, subViewHolder.sender_id, subViewHolder.sender_status)
        }
        else{
            val popUp = ReceivePopUpWindow(context!!, this)
            popUp.start("결제를 진행할까요?", 0, position, subViewHolder.sender_id, subViewHolder.sender_status)
        }
    }

}