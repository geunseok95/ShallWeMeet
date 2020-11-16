package com.professionalandroid.apps.capston_meeting.src.myInfoPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.myInfoPage.models.MyInfoResponse

interface MyInfoPageView {
    fun getInfo(body: MyInfoResponse)
}