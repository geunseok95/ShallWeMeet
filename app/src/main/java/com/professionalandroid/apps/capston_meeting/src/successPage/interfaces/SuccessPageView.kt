package com.professionalandroid.apps.capston_meeting.src.successPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.successPage.models.Is

interface SuccessPageView {
    fun getMakers(makersList: List<Is>)
    fun getSenders(sendersList: List<Is>)
}