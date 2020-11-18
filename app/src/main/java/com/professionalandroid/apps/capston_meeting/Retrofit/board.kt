package com.professionalandroid.apps.capston_meeting.retrofit

import com.google.gson.annotations.SerializedName

data class board(
    @SerializedName("idx")
    var idx: Long,

    @SerializedName("title")
    var title:String,

    @SerializedName("img1")
    var img1:String,

    @SerializedName("img2")
    var img2:String,

    @SerializedName("img3")
    var img3:String,

    @SerializedName("tag1")
    var tag1:String,

    @SerializedName("tag2")
    var tag2:String,

    @SerializedName("tag3")
    var tag3:String,

    @SerializedName("location1")
    var location1:String,

    @SerializedName("location2")
    var location2: String,

    @SerializedName("num_type")
    var num_type:String,

    @SerializedName("age")
    var age:Int,

    @SerializedName("gender")
    var gender:String,

    @SerializedName("createdDate")
    var createdDate:String,

    @SerializedName("updatedDate")
    var updatedDate:String,

    @SerializedName("user")
    var user: user_info,

    @SerializedName("check")
    var check: Boolean
)

data class user_info(
    @SerializedName("idx")
    var idx:Long,

    @SerializedName("nickName")
    var nickName:String,

    @SerializedName("img")
    var img:String,

    @SerializedName("email")
    var email:String,

    @SerializedName("gender")
    var gender:String,

    @SerializedName("age")
    var age:String,

    @SerializedName("location")
    var location:String,

    @SerializedName("kakao_id")
    var kakao_id:String,

    @SerializedName("point")
    var point:String
)

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

data class favorite(
    var code: Int,
    var msg: String
)

data class bookmark(
    var userId: Long,
    var boardId: Long
)

data class email(
    var email: String
)

data class favoriteboard(
    @SerializedName("idx")
    var idx: Long,
    @SerializedName("user")
    var user: user_info,
    @SerializedName("board")
    var board: board
)