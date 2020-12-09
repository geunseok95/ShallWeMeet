package com.professionalandroid.apps.capston_meeting.src.successPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.src.successPage.interfaces.SuccessPageView
import com.professionalandroid.apps.capston_meeting.src.successPage.models.Is
import com.professionalandroid.apps.capston_meeting.src.successPage.successReceivePage.SuccessMakersRecyclerViewAdapter
import com.professionalandroid.apps.capston_meeting.src.successPage.successReceivePage.SuccessReceivePage
import com.professionalandroid.apps.capston_meeting.src.successPage.successSendPage.SuccessSendPage
import com.professionalandroid.apps.capston_meeting.src.successPage.successSendPage.SuccessSendersRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_success_page.view.*

class SuccessPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_success_page, container, false)

        val successReceivePage = SuccessReceivePage()
        val successSendPage = SuccessSendPage()

        val ft = (activity as MainActivity).supportFragmentManager
        var nowPage = view.success_tab.selectedTabPosition

        ft.beginTransaction().replace(R.id.success_container, successReceivePage).commit()

       view.success_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
           override fun onTabReselected(tab: TabLayout.Tab?) {
           }

           override fun onTabUnselected(tab: TabLayout.Tab?) {
           }

           override fun onTabSelected(tab: TabLayout.Tab?) {
               val newPage = tab?.position
               if (nowPage != newPage){
                   when(newPage){
                       0 -> ft.beginTransaction().replace(R.id.success_container, successReceivePage).commit()
                       1 -> ft.beginTransaction().replace(R.id.success_container, successSendPage).commit()
                   }
                   nowPage = newPage!!
               }
           }

       })
        return view
    }

}