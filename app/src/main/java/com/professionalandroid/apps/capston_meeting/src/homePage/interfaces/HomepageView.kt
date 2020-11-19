package com.professionalandroid.apps.capston_meeting.src.homePage.interfaces

import com.professionalandroid.apps.capston_meeting.src.homePage.models.RecommendationResponse

interface HomepageView {
    fun hotTime(hotTimeList: List<RecommendationResponse>)
    fun hotNear(hotNearList: List<RecommendationResponse>)
}