package com.professionalandroid.apps.capston_meeting.src.successPage.successReceivePage

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
import kotlinx.android.synthetic.main.fragment_success_receive_page.view.*

class SuccessReceivePage() : Fragment(), SuccessPageView {

    lateinit var mSuccessPageService: SuccessPageService
    lateinit var mMakersRecyclerView: RecyclerView
    lateinit var mMakersRecyclerViewAdapter: SuccessMakersRecyclerViewAdapter
    val makersList = mutableListOf<Is>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSuccessPageService = SuccessPageService(this, context)
        mMakersRecyclerViewAdapter =
            SuccessMakersRecyclerViewAdapter(
                makersList,
                context
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makersList.clear()
        mSuccessPageService.getSuccess(MainActivity.user)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_success_receive_page, container, false)

        mMakersRecyclerView = view.success_makers_recyclerview
        mMakersRecyclerView.layoutManager = LinearLayoutManager(context)
        mMakersRecyclerView.adapter = mMakersRecyclerViewAdapter

        return view
    }

    override fun getMakers(makersList: List<Is>) {
        this.makersList.addAll(makersList)
        mMakersRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun getSenders(sendersList: List<Is>) {
    }

}