package com.professionalandroid.apps.capston_meeting.src.checkPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.ReceivePage
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.SendPage
import kotlinx.android.synthetic.main.fragment_check_page.view.*


class CheckPage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_check_page, container, false)

        val receivePage = ReceivePage()
        val sendPage = SendPage()

        val ft = (activity as MainActivity).supportFragmentManager
        var nowPage = view.check_tab.selectedTabPosition

        ft.beginTransaction().replace(R.id.check_fragment, receivePage).commit()

        view.check_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val newPage = tab?.position
                if (nowPage != newPage){
                    when(newPage){
                        0 -> ft.beginTransaction().replace(R.id.check_fragment, receivePage).commit()
                        1 -> ft.beginTransaction().replace(R.id.check_fragment, sendPage).commit()
                    }
                    nowPage = newPage!!
                }
            }

        })

        return view
    }


}