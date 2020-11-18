package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models

data class DefaultResponse(
    val code: Int,
    val msg: String
)

data class Permit(
    val boardId: Long,
    val senderId: Long
)