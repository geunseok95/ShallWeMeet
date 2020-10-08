package com.professionalandroid.apps.capston_meeting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG_LIST_HOMEPAGE = "TAG_LIST_HOMEPAGE"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homepage = HomePage()
        val applypage = ApplyPage()
        val waitpage = WaitPage()
        val successpage = SuccessPage()
        val myinfo = MyInfo()
        val mfragment = SlideViewPager()

        val ft = supportFragmentManager

        ft.beginTransaction().replace(R.id.main_layout, homepage).commitAllowingStateLoss()

        bottom_navigation_bar.setOnNavigationItemSelectedListener {


            when(it.itemId){
                R.id.navigation_bar_home -> ft.beginTransaction().replace(R.id.main_layout, homepage).commitAllowingStateLoss()
                R.id.navigation_bar_apply -> ft.beginTransaction().replace(R.id.main_layout, applypage).commitAllowingStateLoss()
                R.id.navigation_bar_favorite -> ft.beginTransaction().replace(R.id.main_layout, waitpage).commitAllowingStateLoss()
                R.id.navigation_bar_success -> ft.beginTransaction().replace(R.id.main_layout, successpage).commitAllowingStateLoss()
                R.id.navigation_bar_myinfo -> ft.beginTransaction().replace(R.id.main_layout, myinfo).commitAllowingStateLoss()
            }

            return@setOnNavigationItemSelectedListener true

        }

    }

    fun move_next_fragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun close_fragment(fragment: Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
        manager.popBackStack()
    }

    fun replace_fragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.new_meeting, fragment)
        transaction.commit()
    }
}