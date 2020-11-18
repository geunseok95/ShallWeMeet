package com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.models.SendResponse

interface SendPageView {
    fun getSend(body: List<SendResponse>)
}