package com.professionalandroid.apps.capston_meeting.src.bookmarkPage.models

data class BookmarkResponse (
    val idx: Long,
    val user: User,
    val board: Board
)

data class Board (
    val idx: Long,
    val title: String,
    val img1: String,
    val img2: String,
    val img3: String,
    val tag1: String,
    val tag2: String,
    val tag3: String,
    val location1: String,
    val location2: String,
    val num_type: String,
    val age: Long,
    val gender: String,
    val date: String,
    val date2: String,
    val createdDate: String,
    val updatedDate: String,
    val user: User
)

data class User (
    val idx: Long,
    val nickName: String,
    val img: String,
    val email: String,
    val gender: String,
    val age: String,
    val location: String,
    val kakao_id: String,
    val point: Long,
    val token: String
)