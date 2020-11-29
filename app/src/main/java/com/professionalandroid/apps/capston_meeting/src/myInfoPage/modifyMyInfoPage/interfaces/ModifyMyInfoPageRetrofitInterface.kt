package com.professionalandroid.apps.capston_meeting.src.myInfoPage.modifyMyInfoPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.myInfoPage.modifyMyInfoPage.models.ModifyMyInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ModifyMyInfoPageRetrofitInterface {
    @Multipart
    @PATCH("api/users/{idx}/")
    fun modifyMyInfo(
        @Path("idx") idx: Long,
        @Part img: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
    ):Call<ModifyMyInfoResponse>
}