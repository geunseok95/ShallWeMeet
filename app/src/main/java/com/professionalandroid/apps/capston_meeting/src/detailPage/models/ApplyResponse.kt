package com.professionalandroid.apps.capston_meeting.src.detailPage.models

data class ApplyResponse(
    val code: Int,
    val msg: String
)

data class ApplyBody(
    val boardId: Long,
    val senderId: Long,
    val status: Boolean
)