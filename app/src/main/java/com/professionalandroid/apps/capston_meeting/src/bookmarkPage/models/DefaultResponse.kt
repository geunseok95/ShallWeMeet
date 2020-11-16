package com.professionalandroid.apps.capston_meeting.src.bookmarkPage.models

data class DefaultResponse(
    val code: Int,
    val msg: String
)

data class Bookmark(
    var userId: Long,
    var boardId: Long
)
