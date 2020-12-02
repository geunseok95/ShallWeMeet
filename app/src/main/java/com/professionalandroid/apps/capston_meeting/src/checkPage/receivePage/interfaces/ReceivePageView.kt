package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.interfaces

import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.ReceiveResponse

interface ReceivePageView {
    fun getReceive(body: List<ReceiveResponse>)
    fun success()
}