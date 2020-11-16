package com.professionalandroid.apps.capston_meeting.src.applyPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.applyPage.models.ApplyResponse

interface ApplyPageView {
    fun setBoard(new_boards: List<ApplyResponse>?)
}