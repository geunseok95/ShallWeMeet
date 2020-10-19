package com.professionalandroid.apps.capston_meeting

import com.professionalandroid.apps.capston_meeting.retrofit._links2

data class list_item_data(
    val idx: Long?,
    val title: String?,
    val img1: String?,
    val img2: String?,
    val img3: String?,
    val keyword: String?,
    val location: String?,
    val num_type: String?,
    val gender: String?,
    val createdDate: String?,
    val updatedDate : String?,
    val age: String?,
    val _links: _links2?
)