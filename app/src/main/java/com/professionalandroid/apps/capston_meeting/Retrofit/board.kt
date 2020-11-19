package com.professionalandroid.apps.capston_meeting.retrofit



data class userid(
    var code: Int,
    var msg: String,
    var _checked: Boolean,
    var idx: Long
)

data class new_user(
    var code: Int,
    var msg: String,
    var idx:Long
)


data class Verification(
    var email: String,
    var token: String
)


