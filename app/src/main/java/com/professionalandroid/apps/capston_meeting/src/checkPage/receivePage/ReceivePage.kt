package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage

import android.content.Context
import android.os.Bundle
import android.util.Log
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

class ReceivePage : Fragment(), ReceivePageView,ReceivePageRecyclerViewAdapter.OnReceiveClicked, ReceivePopUpWindow.MyDialogOKClickedListener, ReceivePageSubRecyclerViewAdapter.OnReceiverClicked , ReceivePageUserDialog.DialogClicked {

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


        // You need to retain one page on each side so that the next and previous items are visible
        view.receive_recyclerview.offscreenPageLimit = 1

// Add a PageTransformer that translates the next and previous items horizontally
// towards the center of the screen, which makes them visible
        val nextItemVisiblePx = resources.getDimension(R.dimen.pageMargin)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.pageMargin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.15f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        view.receive_recyclerview.setPageTransformer(pageTransformer)


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

    override fun successMatch(parent_position: Int, position: Int) {

        val popup = ReceivePageUserDialog(context!!, this)
        popup.start(
            context!!,
            receiveList[parent_position].senders[position].idx,
            receiveList[parent_position].senders[position].status,
            receiveList[parent_position].idx,
            receiveList[parent_position].senders[position].img,
            receiveList[parent_position].senders[position].nickName,
            receiveList[parent_position].senders[position].age,
            receiveList[parent_position].senders[position].location1,
            receiveList[parent_position].senders[position].location2)

    }

    override fun onOKClicked(success: Int, senderId: Long, status: Boolean,  boardId: Long) {
        if(success == 1){
            val permit = Permit(boardId, senderId)
            mReceivePageService.trySuccess(permit)
        }
        else{
            val permit = Permit(boardId, senderId)
            mReceivePageService.tryPaySuccess(permit)
        }
    }

    override fun getReceive(body: List<ReceiveResponse>) {
        receiveList.addAll(body)
        mReceivePageRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun success() {
        receiveList.clear()
        mReceivePageService.getReceive(user)
        mReceivePageRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun accept(senderId: Long, sender_status: Boolean, boardId: Long) {
        if(sender_status){
            val popUp = ReceivePopUpWindow(context!!, this)
            popUp.start("신청을 수락할까요?", 1, senderId, sender_status, boardId)
        }
        else{
            val popUp = ReceivePopUpWindow(context!!, this)
            popUp.start("결제를 진행할까요?", 0, senderId, sender_status, boardId)
        }

    }
}
