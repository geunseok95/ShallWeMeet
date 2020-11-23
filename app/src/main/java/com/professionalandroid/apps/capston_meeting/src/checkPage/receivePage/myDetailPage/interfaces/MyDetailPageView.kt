package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.models.MyDetailResponse

interface MyDetailPageView {
    fun getMyDetail(body: MyDetailResponse)
    fun deletePage()
}