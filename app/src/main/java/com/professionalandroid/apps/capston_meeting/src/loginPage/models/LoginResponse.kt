package com.professionalandroid.apps.capston_meeting.src.loginPage.models

data class LoginResponse(
    val code: Int,
    val msg: String,
    val idx: Long,
    val _checked: Boolean
)

data class Verification(
    val email: String,
    val token: String
)