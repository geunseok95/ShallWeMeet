package com.professionalandroid.apps.capston_meeting.Retrofit

import com.google.gson.annotations.SerializedName

data class boards(
    var _embedded: _embedded,
    var _links: _links,
    var page: page
)

data class _embedded(
    @SerializedName("boards")
    var board_list: List<board>

)

data class _links(
    var self: self,
    var profile: profile
)

data class self(
    var href:String
)

data class profile(
    var href:String
)

data class page(
    @SerializedName("size")
    var size:Int,
    @SerializedName("totalElements")
    var totalElements: Int,
    @SerializedName("totalPages")
    var totalPages: Int,
    @SerializedName("number")
    var number: Int
)



