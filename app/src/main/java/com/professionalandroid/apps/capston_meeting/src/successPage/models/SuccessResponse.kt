package com.professionalandroid.apps.capston_meeting.src.successPage.models

import android.provider.ContactsContract

data class SuccessResponse (
    val is_senders: List<Is>,
    val is_makers: List<Is>
)

data class Is (
    val idx: Long,
    val sender: Sender,
    val board: Board,
    val status: Boolean,
    val createdTime: String,
    val _matched: Boolean
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
    val date: String,
    val gender: String,
    val createdDate: String,
    val updatedDate: String,
    val user: Sender
)

data class Sender (
    val idx: Long,
    val nickName: String,
    val img: String,
    val email: String,
    val gender: String,
    val age: String,
    val location1: String,
    val location2: String,
    val phone: String,
    val point: Long
)