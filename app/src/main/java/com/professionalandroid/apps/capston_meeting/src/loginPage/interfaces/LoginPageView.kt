package com.professionalandroid.apps.capston_meeting.src.loginPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.loginPage.models.LoginResponse

interface LoginPageView {
    fun successValidation(body: LoginResponse)
    fun failValidation()
}