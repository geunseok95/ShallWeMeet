package com.professionalandroid.apps.capston_meeting.src.homePage.interfaces

import com.professionalandroid.apps.capston_meeting.src.homePage.models.RecommendationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomepageRetrofitInterface {
    @GET("api/boards/rec/time/{idx}")
    fun hotTime(
        @Path("idx") idx: Long
    ):Call<List<RecommendationResponse>>

    @GET("api/boards/rec/location/{idx}")
    fun hotNear(
        @Path("idx") idx: Long
    ):Call<List<RecommendationResponse>>
}