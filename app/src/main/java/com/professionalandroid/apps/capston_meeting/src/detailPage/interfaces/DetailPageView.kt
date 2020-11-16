package com.professionalandroid.apps.capston_meeting.src.detailPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.detailPage.models.DetailResponse

interface DetailPageView {
    fun getDetail(body: DetailResponse)
}